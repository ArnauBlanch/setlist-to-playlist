package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.entities.Song;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.base.MusicPlatformRepository;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyImage;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist.SpotifyPlaylistTrackArtist;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpotifyRepository implements MusicPlatformRepository {
    private static final int GET_ARTISTS_MAX_IDS = 50;

    private final SpotifyApiService apiService;

    @Value("${spotify.topTracksPlaylistId}")
    private String topTracksPlaylistId;

    public Optional<Artist> getArtist(String name) {
        return apiService.searchArtist(name).map(SpotifyMapper::mapArtist);
    }

    public Optional<Song> getSong(String artistName, String songName, boolean isCover) {
        return apiService.searchTrack(artistName, songName)
                .map(spotifyTrack -> SpotifyMapper.mapSong(spotifyTrack, isCover));
    }

    @Override
    public List<Artist> getTopArtists() {
        var topTracks = apiService.getPlaylist(topTracksPlaylistId).getTracks().getItems();
        var topTracksArtistIds = topTracks.stream()
                .flatMap(track -> track.getTrack().getAlbum().getArtists().stream())
                .map(SpotifyPlaylistTrackArtist::getId)
                .distinct()
                .limit(GET_ARTISTS_MAX_IDS)
                .toList();

        return apiService.getSeveralArtists(topTracksArtistIds).stream()
                .map(artist -> Artist.builder()
                        .name(artist.getName())
                        .imageUrl(artist.getImages().stream().findFirst().map(SpotifyImage::getUrl).orElse(null))
                        .build()).toList();
    }
}
