package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import com.google.gson.GsonBuilder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.arnau.setlisttoplaylist.domain.MusicPlatformAuthException;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.*;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class SpotifyApiServiceTest {
    private static final String SPOTIFY_ID = "spotify123";
    private static final String SONG_NAME = "Reptilia";
    private static final String ARTIST = "The Strokes";

    private final MockWebServer mockWebServer = new MockWebServer();
    private final SpotifyApi spotifyApi = new Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApi.class);

    private final SpotifyApiService apiService = new SpotifyApiService(spotifyApi);

    @Nested
    class SearchTrack {

        @Test
        public void when200AndSongMatches_ReturnsSong() {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(toJson(searchResult(
                            TrackItem.builder()
                                    .id(SPOTIFY_ID)
                                    .name(SONG_NAME)
                                    .artists(singletonList(ArtistItem.builder().name(ARTIST).build()))
                                    .build()))));

            var track = apiService.searchTrack(ARTIST, SONG_NAME);

            assertThat(track).isNotEmpty();
            assertThat(track.get().getId()).isEqualTo(SPOTIFY_ID);
            assertThat(track.get().getName()).isEqualTo(SONG_NAME);
        }

        @Test
        public void when200AndSongDoesNotMatch_ReturnsNull() {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(toJson(searchResult(
                            TrackItem.builder()
                                    .id(SPOTIFY_ID)
                                    .name(SONG_NAME)
                                    .artists(singletonList(ArtistItem.builder().name("Wrong Artist").build()))
                                    .build()))));

            var track = apiService.searchTrack(ARTIST, SONG_NAME);

            assertThat(track).isEmpty();
        }

        @Test
        public void when200AndSongIsNotFound_ReturnsNull() {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(toJson(searchResult(null))));

            var track = apiService.searchTrack(ARTIST, SONG_NAME);

            assertThat(track).isEmpty();
        }

        @Test
        public void whenNot200_ThrowsException() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(UNAUTHORIZED.value()));

            assertThrows(RuntimeException.class, () -> apiService.searchTrack(ARTIST, SONG_NAME));
        }
    }

    @Nested
    class GetUserId {
        private static final String USER_ID = "spotifyuserid";
        private static final String USER_TOKEN = "spotify_user_token";

        @Test
        public void when200_ReturnsUserId() {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(toJson(new MeResponse(USER_ID))));

            var userId = apiService.getUserId(USER_TOKEN);

            assertThat(userId).isEqualTo(USER_ID);
        }

        @Test
        public void when401_throwsMusicPlatformAuthException() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(UNAUTHORIZED.value()));

            assertThrows(MusicPlatformAuthException.class, () -> apiService.getUserId(USER_TOKEN));
        }
    }

    @Nested
    class CreatePlaylist {
        private static final String USER_ID = "spotifyuserid";
        private static final String AUTHORIZATION_HEADER = "Bearer spotify_user_token";

        @Test
        public void when200AndPlaylistCreated_ReturnsPlaylist() {
            String playlistId = "playlist123";
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(toJson(new PlaylistCreatedResponse(playlistId))));

            var playlist = apiService.createPlaylist(USER_ID,
                    new CreatePlaylistRequest("name", "description", false),
                    AUTHORIZATION_HEADER);

            assertThat(playlist.id()).isEqualTo(playlistId);
        }

        @Test
        public void when401_throwsMusicPlatformAuthException() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(UNAUTHORIZED.value()));

            assertThrows(MusicPlatformAuthException.class, () -> apiService.getUserId(AUTHORIZATION_HEADER));
        }
    }

    private String toJson(Object result) {
        return new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create()
                .toJson(result);
    }

    private SearchResponse searchResult(TrackItem track) {
        return new SearchResponse(new TracksSearchResult(
                track != null ? singletonList(track) : emptyList()));
    }
}