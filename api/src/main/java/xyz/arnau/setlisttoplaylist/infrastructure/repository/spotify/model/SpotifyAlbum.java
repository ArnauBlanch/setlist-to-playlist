package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class SpotifyAlbum {
    String name;
    List<SpotifyImage> images;
}
