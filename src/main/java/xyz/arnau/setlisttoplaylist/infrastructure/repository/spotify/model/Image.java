package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public final class Image {
    private final String url;
}
