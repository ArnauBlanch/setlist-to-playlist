package xyz.arnau.setlisttoplaylist.domain.entities;

import lombok.Builder;

@Builder
public record Song(
        String musicPlatformId,
        String name,
        String albumName,
        String albumCoverUrl,
        int durationSeconds,
        String previewUrl,
        String originalArtistName
) {}
