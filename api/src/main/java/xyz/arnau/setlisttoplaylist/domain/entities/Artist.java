package xyz.arnau.setlisttoplaylist.domain.entities;

import lombok.Builder;

@Builder
public record Artist(
        String id,
        String musicPlatformId,
        String name,
        String imageUrl
) {}
