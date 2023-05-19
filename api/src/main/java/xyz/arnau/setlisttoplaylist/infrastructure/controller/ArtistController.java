package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.arnau.setlisttoplaylist.application.ArtistService;
import xyz.arnau.setlisttoplaylist.application.SetlistService;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.mapper.ArtistMapper;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.mapper.SetlistMapper;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.response.ArtistResponse;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.response.ArtistSetlistsResponse;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.response.PlaylistResponse;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/artists")
@Tag(name = "Artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final SetlistService setlistService;

    @GetMapping("top")
    @Operation(summary = "Get top artists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top artists found", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = PlaylistResponse.class))
            }),
    })
    public ResponseEntity<List<ArtistResponse>> getTopArtists(
            @RequestParam @Parameter(description = "Number of artist to return", example = "5") int count) {
        var randomTopArtists = new ArrayList<>(artistService.getTopArtists());
        shuffle(randomTopArtists);

        return ResponseEntity.ok(randomTopArtists
                .subList(0, count).stream()
                .map(ArtistMapper.MAPPER::toResponse)
                .collect(toList()));
    }

    @GetMapping
    @Operation(summary = "Get artists by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artists found", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = PlaylistResponse.class))
            }),
    })
    public ResponseEntity<List<ArtistResponse>> searchArtistsByName(
            @RequestParam @Parameter(description = "Name of the artist", example = "Arctic Monkeys") String name) {
        return ResponseEntity.ok(artistService.searchByName(name).stream()
                .map(ArtistMapper.MAPPER::toResponse)
                .collect(toList()));
    }

    @GetMapping("{artistId}")
    @Operation(summary = "Get artist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist found", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ArtistResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Artist not found", content = @Content)
    })
    public ResponseEntity<ArtistResponse> getArtistSetlists(
            @PathVariable @Parameter(description = "Artist ID", example = "d15721d8-56b4-453d-b506-fc915b14cba2") String artistId) {
        var artist = artistService.getById(artistId);
        return ResponseEntity.ok(ArtistMapper.MAPPER.toResponse(artist));
    }

    @GetMapping("{artistId}/setlists")
    @Operation(summary = "Get artist setlists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist setlists found", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ArtistSetlistsResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Artist setlists not found", content = @Content)
    })
    public ResponseEntity<ArtistSetlistsResponse> getArtistSetlists(
            @PathVariable @Parameter(description = "Artist ID", example = "d15721d8-56b4-453d-b506-fc915b14cba2") String artistId,
            @RequestParam(defaultValue = "1") @Parameter(description = "Page number, starting from 1", example = "1") int page) {
        var setlistsPage = setlistService.getByArtistId(artistId, page);
        return ResponseEntity.ok(ArtistSetlistsResponse.builder()
                .setlists(setlistsPage.items().stream().map(SetlistMapper.MAPPER::toResponse).toList())
                .page(setlistsPage.page())
                .totalItems(setlistsPage.totalItems())
                .itemsPerPage(setlistsPage.itemsPerPage())
                .build());
    }
}p