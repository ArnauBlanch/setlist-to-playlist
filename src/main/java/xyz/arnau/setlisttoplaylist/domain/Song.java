package xyz.arnau.setlisttoplaylist.domain;

import lombok.Builder;

@Builder
public record Song(String id, String name, String album, String albumCoverUrl, int durationSeconds,
                   String previewUrl, String originalArtist) {}
