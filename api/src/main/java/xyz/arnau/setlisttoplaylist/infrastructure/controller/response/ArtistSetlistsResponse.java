package xyz.arnau.setlisttoplaylist.infrastructure.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(requiredProperties = {"setlists", "page", "totalItems", "itemsPerPage"})
@Builder
public record ArtistSetlistsResponse(
        @Schema(description = "Artist setlists")
        List<BasicSetlistResponse> setlists,
        @Schema(description = "Page number", example = "1")
        int page,
        @Schema(description = "Total number of setlists for the artist", example = "56")
        int totalItems,
        @Schema(description = "Number of items per page", example = "20")
        int itemsPerPage
) {
}
