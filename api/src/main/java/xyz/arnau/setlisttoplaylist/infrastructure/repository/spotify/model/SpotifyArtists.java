package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Value;

import java.util.List;

@Value
public class SpotifyArtists {
    List<SpotifyArtist> artists;
}
