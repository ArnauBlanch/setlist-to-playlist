package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Value;

@Value
public class SpotifySearchResult {
    SpotifySearchResultItem<SpotifyTrack> tracks;
    SpotifySearchResultItem<SpotifyArtist> artists;
}
