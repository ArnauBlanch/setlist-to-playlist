package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.arnau.setlisttoplaylist.application.PlaylistService;
import xyz.arnau.setlisttoplaylist.application.SetlistService;
import xyz.arnau.setlisttoplaylist.domain.Setlist;
import xyz.arnau.setlisttoplaylist.domain.SetlistNotFoundException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/setlists")
@RequiredArgsConstructor
public class SetlistController {

    private final SetlistService setlistService;
    private final PlaylistService playlistService;

    @GetMapping("{setlistId}")
    public ResponseEntity<Setlist> getSetlist(@PathVariable String setlistId) {
        return setlistService.getSetlist(setlistId)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @PostMapping("{setlistId}/playlist")
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
}
