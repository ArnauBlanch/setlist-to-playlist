package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public final class AlbumItem {
    private final String name;
    private final List<Image> images;
}
