package xyz.arnau.setlisttoplaylist.infrastructure.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(requiredProperties = {"date", "artist", "venue", "songs"})
public record SetlistResponse(
        @Schema(description = "Setlist date")
        LocalDate date,
        ArtistResponse artist,
        VenueResponse venue,
        List<SongResponse> songs
) {
}
