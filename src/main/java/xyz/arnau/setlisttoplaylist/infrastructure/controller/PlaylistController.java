package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.arnau.setlisttoplaylist.application.PlaylistService;
import xyz.arnau.setlisttoplaylist.domain.SetlistNotFoundException;

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
