package xyz.arnau.setlisttoplaylist.domain.entities;

import lombok.Builder;

@Builder
public record Venue(
        String name,
        String city,
        String country,
        String countryCode
) {
}
