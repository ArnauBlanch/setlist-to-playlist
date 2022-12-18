package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist;

import lombok.Value;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
public class SpotifyPlaylistTrack {
    SpotifyPlaylistTrackInfo track;

    public static SpotifyPlaylistTrack create(List<String> artistIds) {
        return new SpotifyPlaylistTrack(
                new SpotifyPlaylistTrackInfo(
                        new SpotifyPlaylistTrackAlbum(artistIds.stream()
                                .map(SpotifyPlaylistTrackArtist::new)
                                .collect(toList()))));
    }
}
