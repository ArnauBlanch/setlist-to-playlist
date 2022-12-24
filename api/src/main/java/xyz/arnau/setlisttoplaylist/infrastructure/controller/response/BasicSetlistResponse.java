package xyz.arnau.setlisttoplaylist.infrastructure.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(requiredProperties = {"id", "date", "venue", "numSongs"})
public record BasicSetlistResponse(
        @Schema(description = "Setlist ID", example = "6bbf4e1e")
        String id,
        @Schema(description = "Setlist date")
        LocalDate date,
        VenueResponse venue,
        @Schema(description = "Number of songs", example = "23")
        int numSongs
) {
}
