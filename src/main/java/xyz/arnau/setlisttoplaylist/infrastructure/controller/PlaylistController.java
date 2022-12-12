package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.arnau.setlisttoplaylist.application.PlaylistService;
import xyz.arnau.setlisttoplaylist.domain.MusicPlatformAuthException;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.mapper.PlaylistMapper;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.response.PlaylistResponse;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/playlists")
@Tag(name = "Playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping("{setlistId}")
    @Operation(summary = "Create playlist from setlist",
                security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Playlist created", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = PlaylistResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Setlist not found", content = @Content),
    })
    public ResponseEntity<PlaylistResponse> createPlaylistFromSetlist(
            @PathVariable @Parameter(description = "Setlist ID", example = "6bb43616") String setlistId,
            @RequestParam(required = false, defaultValue = "false")
            @Parameter(description = "Indicates if playlist should be public") boolean isPublic,
            @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true) String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new MusicPlatformAuthException();

        var playlist = playlistService.createFromSetlist(setlistId, isPublic, authorizationHeader);
        return status(CREATED).body(PlaylistMapper.MAPPER.toResponse(playlist));
    }

    @GetMapping("{setlistId}/cover")
    @Operation(summary = "Get playlist cover")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist cover generated", content = {
                    @Content(mediaType = IMAGE_JPEG_VALUE)
            }),
            @ApiResponse(responseCode = "404", description = "Setlist not found", content = @Content),
    })
    public ResponseEntity<byte[]> getPlaylistCover(
            @PathVariable @Parameter(description = "Setlist ID", example = "6bb43616") String setlistId) {
        var imageBytes = playlistService.getCoverImage(setlistId);
        return ok().contentType(IMAGE_JPEG).body(imageBytes);
    }
}
