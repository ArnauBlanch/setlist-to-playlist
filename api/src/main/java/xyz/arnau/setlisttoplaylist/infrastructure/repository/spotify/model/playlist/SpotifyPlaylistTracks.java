package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist;

import lombok.Value;

import java.util.List;

@Value
public class SpotifyPlaylistTracks {
    List<SpotifyPlaylistTrack> items;
}
