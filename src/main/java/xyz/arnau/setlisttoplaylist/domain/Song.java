package xyz.arnau.setlisttoplaylist.domain;

import lombok.Builder;

@Builder
public record Song(String id, String name, String albumName, String albumCoverUrl, int durationSeconds,
                   String previewUrl, String originalArtist) {}
