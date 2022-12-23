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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.arnau.setlisttoplaylist.application.ArtistService;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.mapper.ArtistMapper;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.response.ArtistResponse;
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
}
