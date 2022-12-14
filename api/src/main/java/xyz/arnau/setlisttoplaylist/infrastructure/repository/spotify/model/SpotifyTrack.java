package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class SpotifyTrack {
    String id;
    String name;
    int durationMs;
    String previewUrl;
    List<SpotifyArtist> artists;
    SpotifyAlbum album;
}
