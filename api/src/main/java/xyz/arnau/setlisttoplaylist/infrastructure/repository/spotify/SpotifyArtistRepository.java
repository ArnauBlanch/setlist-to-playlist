package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.ports.ArtistRepository;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyImage;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist.SpotifyPlaylistTrackArtist;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpotifyArtistRepository implements ArtistRepository {
    private static final int GET_ARTISTS_MAX_IDS = 50;
    private final SpotifyApiService spotifyApiService;

    @Value("${spotify.topTracksPlaylistId}")
    private String topTracksPlaylistId;

    @Cacheable(value = "topArtists", unless="#result == null")
    public List<Artist> getTopArtists() {
        var topTracks = spotifyApiService.getPlaylist(topTracksPlaylistId).getTracks().getItems();
        var topTracksArtistIds = topTracks.stream()
                .flatMap(track -> track.getTrack().getAlbum().getArtists().stream())
                .map(SpotifyPlaylistTrackArtist::getId)
                .distinct()
                .limit(GET_ARTISTS_MAX_IDS)
                .toList();

        return spotifyApiService.getSeveralArtists(topTracksArtistIds).stream()
                .map(artist -> Artist.builder()
                        .name(artist.getName())
                        .imageUrl(artist.getImages().stream().findFirst().map(SpotifyImage::getUrl).orElse(null))
                        .build()).toList();
    }
}
