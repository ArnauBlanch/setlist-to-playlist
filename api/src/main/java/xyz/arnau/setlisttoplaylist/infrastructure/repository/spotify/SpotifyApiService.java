package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import xyz.arnau.setlisttoplaylist.domain.exceptions.MusicPlatformAuthException;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyTrack;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyUserProfile;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist.CreatePlaylistRequest;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist.SpotifyPlaylist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist.SpotifyPlaylistCreated;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.join;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
@CommonsLog
public class SpotifyApiService {

    private static final String DEFAULT_MARKET = "ES";
    public static final String GET_PLAYLIST_FIELDS = "tracks.items(track(album(artists)))";

    private final SpotifyApi spotifyApi;

    public Optional<SpotifyTrack> searchTrack(String artist, String trackName) {
        try {
            var response = spotifyApi.search(searchSongQuery(artist, trackName), "track", 1, DEFAULT_MARKET).execute();
            if (response.isSuccessful() && response.body() != null) {
                var trackItems = response.body().getTracks().getItems();
                if (!trackItems.isEmpty()) {
                    var trackItem = trackItems.get(0);
                    if (trackItem.getArtists().stream().anyMatch(a -> a.getName().equalsIgnoreCase(artist))) {
                        return Optional.of(trackItem);
                    }
                }
            } else {
                log.error("Could not fetch track (name=%s, artist=%s)".formatted(trackName, artist));
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            log.error("Could not fetch track (name=%s, artist=%s)".formatted(trackName, artist));
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<SpotifyArtist> searchArtist(String artistName) {
        try {
            var response = spotifyApi.search(searchArtistQuery(artistName), "artist", 1, DEFAULT_MARKET).execute();
            if (response.isSuccessful() && response.body() != null) {
                var artistItems = response.body().getArtists().getItems();
                if (!artistItems.isEmpty()) {
                    var artistItem = artistItems.get(0);
                    return Optional.of(artistItem);
                }
            } else if (response.code() == HttpStatus.NOT_FOUND.value()) {
                return Optional.empty();
            } else {
                log.error("Could not fetch artist (name=%s)".formatted(artistName));
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            log.error("Could not fetch artist (name=%s)".formatted(artistName));
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public String getUserId(String authorizationHeader) {
        try {
            Response<SpotifyUserProfile> response = spotifyApi.getUserId(authorizationHeader).execute();
            if (response.isSuccessful() && response.body() != null && response.body().getId() != null) {
                return response.body().getId();
            } else if (response.code() == UNAUTHORIZED.value()) {
                throw new MusicPlatformAuthException();
            } else {
                log.error("Could not fetch user id");
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            log.error("Could not fetch user id");
            throw new RuntimeException(e);
        }
    }

    public SpotifyPlaylistCreated createPlaylist(String userId, CreatePlaylistRequest createPlaylistRequest, String authorizationHeader) {
        try {
            var response = spotifyApi.createPlaylist(userId, createPlaylistRequest, authorizationHeader).execute();
            if (response.isSuccessful() && response.body() != null && response.body().getId() != null) {
                return response.body();
            } else if (response.code() == UNAUTHORIZED.value()) {
                throw new MusicPlatformAuthException();
            } else {
                log.error("Could not create playlist");
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            log.error("Could not create playlist");
            throw new RuntimeException(e);
        }
    }

    public void addSongsToPlaylist(String playlistId, List<String> songIds, String authorizationHeader) {
        try {
            Response<Void> response = spotifyApi.addSongsToPlaylist(playlistId,
                    songIds.stream().map(id -> "spotify:track:" + id).collect(Collectors.joining(",")),
                    authorizationHeader).execute();
            if (response.code() == UNAUTHORIZED.value()) {
                throw new MusicPlatformAuthException();
            } else if (response.code() != HttpStatus.CREATED.value()) {
                log.error("Could not add songs to playlist (playlistId=%s)".formatted(playlistId));
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            log.error("Could not add songs to playlist (playlistId=%s)".formatted(playlistId));
            throw new RuntimeException(e);
        }
    }

    public void addCoverImageToPlaylist(String playlistId, byte[] image, String authorizationHeader) {
        try {
            RequestBody body = RequestBody.create(Base64.getEncoder().encodeToString(image), MediaType.get("image/jpeg"));
            Response<Void> response = spotifyApi.addCoverImageToPlaylist(playlistId,
                    body, authorizationHeader).execute();
            if (response.code() == UNAUTHORIZED.value()) {
                throw new MusicPlatformAuthException();
            } else if (response.code() != HttpStatus.ACCEPTED.value()) {
                log.error("Could not add cover image to playlist (playlistId=%s)".formatted(playlistId));
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            log.error("Could not add cover image to playlist (playlistId=%s)".formatted(playlistId));
            throw new RuntimeException(e);
        }
    }

    public SpotifyPlaylist getPlaylist(String playlistId) {
        try {
            var response = spotifyApi.getPlaylist(playlistId, GET_PLAYLIST_FIELDS).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else if (response.code() == UNAUTHORIZED.value()) {
                throw new MusicPlatformAuthException();
            } else {
                log.error("Could not get playlist (playlistId=%s)".formatted(playlistId));
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            log.error("Could not get playlist (playlistId=%s)".formatted(playlistId));
            throw new RuntimeException(e);
        }
    }

    public List<SpotifyArtist> getSeveralArtists(List<String> artistIds) {
        String ids = join(",", artistIds);
        try {
            var response = spotifyApi.getSeveralArtists(ids).execute();
            if (response.isSuccessful() && response.body() != null && response.body().getArtists() != null) {
                return response.body().getArtists();
            } else if (response.code() == UNAUTHORIZED.value()) {
                throw new MusicPlatformAuthException();
            } else {
                log.error("Could not get artists (artistIds=%s)".formatted(ids));
                throw new RuntimeException("Spotify API error");
            }
        } catch (IOException e) {
            log.error("Could not get artists (artistIds=%s)".formatted(ids));
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
