package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public final class TrackItem {
    private final String id;
    private final String name;
    private final int durationMs;
    private final String previewUrl;
    private final List<ArtistItem> artists;
    private final AlbumItem album;
}
