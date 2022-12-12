package xyz.arnau.setlisttoplaylist.infrastructure.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"id", "name", "albumName", "albumCoverUrl", "durationSeconds", "previewUrl"})
public record SongResponse(
        @Schema(description = "Platform track ID", example = "5G1sTBGbZT5o4PNRc75RKI")
        String id,
        @Schema(description = "Song name", example = "Lonely Boy")
        String name,
        @Schema(description = "Album name", example = "El Camino")
        String albumName,
        @Schema(description = "URL of the album cover imageUrl", example = "https://i.scdn.co/image/ab67616d0000b2736a21b97de47168df4f0c1993")
        String albumCoverUrl,
        @Schema(description = "Song duration (in seconds)", example = "193")
        int durationSeconds,
        @Schema(description = "Song preview URL", example = "https://p.scdn.co/mp3-preview/2ad81dc04cb1e29388f918bbf948b5f812632131?cid=ed16feb3c955427dab52122d2515a642")
        String previewUrl,
        @Schema(description = "Name of the original artist (if song is a cover; null, otherwise)", nullable = true)
        String originalArtist
) {
}
