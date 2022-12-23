package xyz.arnau.setlisttoplaylist.domain.entities;

import lombok.Builder;

@Builder
public record Playlist(
        String musicPlatformId
) {
}
