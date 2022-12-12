package xyz.arnau.setlisttoplaylist.infrastructure.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"name", "city", "country", "countryCode"})
public record VenueResponse(
        @Schema(description = "Venue name", example = "Kia Forum")
        String name,
        @Schema(description = "Venue city", example = "Inglewood")
        String city,
        @Schema(description = "Venue country", example = "United States")
        String country,
        @Schema(description = "Code of the venue country", example = "US")
        String countryCode
) {
}
