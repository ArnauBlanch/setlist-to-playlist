package xyz.arnau.setlisttoplaylist.infrastructure.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"name", "imageUrl"})
public record ArtistResponse(
        @Schema(description = "Artist name", example = "The Black Keys")
        String name,
        @Schema(description = "URL of artist image", example = "https://i.scdn.co/image/ab6761610000e5ebae537808bd15be9f7031e99b")
        String imageUrl
) {
}
