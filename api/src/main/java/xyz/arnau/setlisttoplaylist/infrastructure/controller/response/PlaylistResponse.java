package xyz.arnau.setlisttoplaylist.infrastructure.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"musicPlatformId"})
public record PlaylistResponse(
        @Schema(description = "Music platform playlist ID", example = "0MH3Wi1Fxdv08IGIgwADPh")
        String musicPlatformId
) {
}
