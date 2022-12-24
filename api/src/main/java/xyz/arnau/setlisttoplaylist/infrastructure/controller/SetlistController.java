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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.arnau.setlisttoplaylist.application.SetlistService;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.mapper.SetlistMapper;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.response.SetlistResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/setlists")
@Tag(name = "Setlists")
@RequiredArgsConstructor
public class SetlistController {

    private final SetlistService setlistService;

    @GetMapping("{setlistId}")
    @Operation(summary = "Get setlist by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setlist found", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = SetlistResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Setlist not found", content = @Content)
    })
    public ResponseEntity<SetlistResponse> getSetlist(
            @PathVariable @Parameter(description = "Setlist ID", example = "6bb43616") String setlistId) {
        Setlist setlist = setlistService.getById(setlistId);
        return ok(SetlistMapper.MAPPER.toResponse(setlist));
    }
}
