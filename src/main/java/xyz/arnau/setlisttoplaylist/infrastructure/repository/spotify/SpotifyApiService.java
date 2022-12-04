package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.TrackItem;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpotifyApiService {

    private final SpotifyApi spotifyApi;

    @Cacheable(value = "setlists", key = "#p0.concat(#p1)", unless="#result == null")
    public Optional<TrackItem> searchTrack(String artist, String trackName) {
        try {
            var response = spotifyApi.search(query(artist, trackName), "track", 1).execute();
            if (response.isSuccessful() && response.body() != null
                    && !response.body().getTracks().getItems().isEmpty()) {
                TrackItem trackItem = response.body().getTracks().getItems().get(0);
                if (trackItem.getArtists().stream().anyMatch(a -> a.getName().equals(artist))) {
                    return Optional.of(trackItem);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    private static String query(String artist, String trackName) {
        return "artist:%s track:%s" .formatted(artist, trackName);
    }
}
