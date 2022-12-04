package xyz.arnau.setlisttoplaylist.domain;

import lombok.Builder;

@Builder
public record Song(String spotifyId, String name, String album, String albumCoverUrl, int durationSeconds,
                   String previewUrl, String originalArtist) {}
