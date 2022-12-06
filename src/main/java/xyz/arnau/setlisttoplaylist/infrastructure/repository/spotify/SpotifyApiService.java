package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import xyz.arnau.setlisttoplaylist.domain.MusicPlatformAuthException;
import xyz.arnau.setlisttoplaylist.domain.Playlist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.CreatePlaylistRequest;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.MeResponse;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.TrackItem;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class SpotifyApiService {

    private final SpotifyApi spotifyApi;

    @Cacheable(value = "setlists", key = "#p0.concat(#p1)", unless="#result == null")
    public Optional<TrackItem> searchTrack(String artist, String trackName) {
        try {
            var response = spotifyApi.search(query(artist, trackName), "track", 1).execute();
            if (response.isSuccessful() && response.body() != null) {
                var trackItems = response.body().getTracks().getItems();
                if (!trackItems.isEmpty()) {
                    var trackItem = trackItems.get(0);
                    if (trackItem.getArtists().stream().anyMatch(a -> a.getName().equals(artist))) {
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

    private static String query(String artist, String trackName) {
        return "artist:%s track:%s" .formatted(artist, trackName);
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
}
