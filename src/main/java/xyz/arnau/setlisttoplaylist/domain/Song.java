package xyz.arnau.setlisttoplaylist.domain;

import lombok.Builder;

@Builder
public record Song(String name, String originalArtist) {}
