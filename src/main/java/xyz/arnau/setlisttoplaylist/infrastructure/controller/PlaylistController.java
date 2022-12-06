package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import xyz.arnau.setlisttoplaylist.application.PlaylistService;
import xyz.arnau.setlisttoplaylist.domain.SetlistNotFoundException;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static com.google.common.io.Resources.getResource;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping("{setlistId}")
    public ResponseEntity<?> createPlaylistFromSetlist(
            @PathVariable String setlistId,
            @RequestParam(value = "public", required = false, defaultValue = "false") boolean isPublic,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            var playlist = playlistService.createFromSetlist(setlistId, isPublic, authorizationHeader);
            return ResponseEntity.status(CREATED).body(playlist);
        } catch (SetlistNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @GetMapping("{setlistId}/cover")
    public ResponseEntity<byte[]> getPlaylistCover(@PathVariable String setlistId) {
        try {
            var imageBytes = playlistService.getCoverImage(setlistId);
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (SetlistNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
