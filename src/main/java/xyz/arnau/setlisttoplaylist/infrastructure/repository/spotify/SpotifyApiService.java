package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import xyz.arnau.setlisttoplaylist.domain.MusicPlatformAuthException;
import xyz.arnau.setlisttoplaylist.domain.Playlist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.ArtistItem;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.CreatePlaylistRequest;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.MeResponse;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.TrackItem;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class SpotifyApiService {

    private final SpotifyApi spotifyApi;

    @Cacheable(value = "setlists", key = "#p0.concat(#p1)", unless="#result == null")
    public Optional<TrackItem> searchTrack(String artist, String trackName) {
        try {
            var response = spotifyApi.search(searchSongQuery(artist, trackName), "track", 1).execute();
            if (response.isSuccessful() && response.body() != null) {
                var trackItems = response.body().getTracks().getItems();
                if (!trackItems.isEmpty()) {
                    var trackItem = trackItems.get(0);
                    if (trackItem.getArtists().stream().anyMatch(a -> a.getName().equalsIgnoreCase(artist))) {
                        return Optional.of(trackItem);
                    }
                }
            } else {
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Cacheable(value = "artists", key = "#p0", unless="#result == null")
    public Optional<ArtistItem> searchArtist(String artistName) {
        try {
            var response = spotifyApi.search(searchArtistQuery(artistName), "artist", 1).execute();
            if (response.isSuccessful() && response.body() != null) {
                var artistItems = response.body().getArtists().getItems();
                if (!artistItems.isEmpty()) {
                    var artistItem = artistItems.get(0);
                    return Optional.of(artistItem);
                }
            } else {
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public String getUserId(String authorizationHeader) {
        try {
            Response<MeResponse> response = spotifyApi.getUserId(authorizationHeader).execute();
            if (response.isSuccessful() && response.body() != null && response.body().getId() != null) {
                return response.body().getId();
            } else if (response.code() == UNAUTHORIZED.value()) {
                throw new MusicPlatformAuthException(UNAUTHORIZED.name());
            } else {
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Playlist createPlaylist(String userId, CreatePlaylistRequest createPlaylistRequest, String authorizationHeader) {
        try {
            var response = spotifyApi.createPlaylist(userId, createPlaylistRequest, authorizationHeader).execute();
            if (response.isSuccessful() && response.body() != null && response.body().getId() != null) {
                return new Playlist(response.body().getId());
            } else if (response.code() == UNAUTHORIZED.value()) {
                throw new MusicPlatformAuthException(UNAUTHORIZED.name());
            } else {
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSongsToPlaylist(String playlistId, List<String> songIds, String authorizationHeader) {
        try {
            Response<Void> response = spotifyApi.addSongsToPlaylist(playlistId,
                    songIds.stream().map(id -> "spotify:track:" + id).collect(Collectors.joining(",")),
                    authorizationHeader).execute();
            if (response.code() == UNAUTHORIZED.value()) {
                throw new MusicPlatformAuthException(UNAUTHORIZED.name());
            } else if (response.code() != HttpStatus.CREATED.value()) {
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCoverImageToPlaylist(String playlistId, byte[] imageBytes, String authorizationHeader) {
        try {
            Response<Void> response = spotifyApi.addCoverImageToPlaylist(playlistId,
                    RequestBody.create(MediaType.parse("application/png"), Base64.getEncoder().encode(imageBytes)), authorizationHeader).execute();
            if (response.code() == UNAUTHORIZED.value()) {
                throw new MusicPlatformAuthException(UNAUTHORIZED.name());
            } else if (response.code() != HttpStatus.ACCEPTED.value()) {
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String searchSongQuery(String artist, String trackName) {
        return "artist:%s track:%s" .formatted(artist, trackName);
    }

    private static String searchArtistQuery(String artist) {
        return "artist:%s" .formatted(artist);
    }
}
