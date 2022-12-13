package xyz.arnau.setlisttoplaylist.domain.entities;

import lombok.Builder;

@Builder
public record Artist(
        String name,
        String imageUrl
) {}
