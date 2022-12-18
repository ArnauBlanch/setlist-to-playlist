package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist;

import lombok.Value;

import java.util.List;

@Value
public class SpotifyPlaylistTrackAlbum {
    List<SpotifyPlaylistTrackArtist> artists;
}
