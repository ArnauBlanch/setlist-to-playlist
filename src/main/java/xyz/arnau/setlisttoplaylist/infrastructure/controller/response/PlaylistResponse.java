package xyz.arnau.setlisttoplaylist.infrastructure.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"id"})
public record PlaylistResponse(
        @Schema(description = "Playlist ID", example = "0MH3Wi1Fxdv08IGIgwADPh")
        String id
) {
}
