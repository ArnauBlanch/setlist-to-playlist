package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.ports.ArtistRepository;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyMapper;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyImage;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist.SpotifyPlaylistTrackArtist;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.pivovarit.collectors.ParallelCollectors.parallel;
import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class SpotifyArtistRepository implements ArtistRepository {
    private static final int GET_ARTISTS_MAX_IDS = 50;

    private final ExecutorService executorService = Executors.newFixedThreadPool(30);
    private final SetlistFmApiService setlistFmApiService;
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

    public List<Artist> getArtistsByName(String name) {
        try {
            return setlistFmApiService.searchArtists(name).stream()
                    .map(SetlistFmArtist::getSortName)
                    .collect(
                            parallel(searchName -> spotifyApiService.searchArtist(searchName)
                                    .map(SpotifyMapper::mapArtist), toList(), executorService, 10))
                    .get().stream().filter(Optional::isPresent).map(Optional::get).collect(toList());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
