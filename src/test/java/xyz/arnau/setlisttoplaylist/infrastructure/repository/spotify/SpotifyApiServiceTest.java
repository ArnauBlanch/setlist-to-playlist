package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.arnau.setlisttoplaylist.domain.exceptions.MusicPlatformAuthException;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.*;

import java.nio.charset.Charset;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.springframework.http.HttpStatus.*;

class SpotifyApiServiceTest {
    private static final String SPOTIFY_ID = "spotify123";
    private static final String SONG_NAME = "Reptilia";
    private static final String ARTIST = "The Strokes";
    private static final String AUTHORIZATION_HEADER = "Bearer spotify_user_token";
    public static final Gson JSON_MAPPER = new GsonBuilder()
            .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private final MockWebServer mockWebServer = new MockWebServer();
    private final SpotifyApi spotifyApi = new Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder().setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create()))
            .build()
            .create(SpotifyApi.class);

    private final SpotifyApiService apiService = new SpotifyApiService(spotifyApi);

    @Nested
    class SearchTrack {

        @Test
        public void when200AndSongMatches_ReturnsSong() throws InterruptedException {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(JSON_MAPPER.toJson(new SpotifySearchResult(
                            new SpotifySearchResultItem<>(singletonList(SpotifyTrack.builder()
                                    .id(SPOTIFY_ID)
                                    .name(SONG_NAME)
                                    .artists(singletonList(SpotifyArtist.builder().name(ARTIST).build()))
                                    .build())), null))));

            var track = apiService.searchTrack(ARTIST, SONG_NAME);

            assertThat(track).isNotEmpty();
            assertThat(track.get().getId()).isEqualTo(SPOTIFY_ID);
            assertThat(track.get().getName()).isEqualTo(SONG_NAME);

            RecordedRequest request = mockWebServer.takeRequest();
            assertThat(request.getMethod()).isEqualTo("GET");
            assertThat(requireNonNull(request.getRequestUrl()).encodedPath()).isEqualTo("/v1/search");
            assertThat(request.getRequestUrl().queryParameter("q"))
                    .isEqualTo("artist:" + ARTIST + " track:" + SONG_NAME);
            assertThat(request.getRequestUrl().queryParameter("type")).isEqualTo("track");
            assertThat(request.getRequestUrl().queryParameter("limit")).isEqualTo("1");
        }

        @Test
        public void when200AndSongDoesNotMatch_ReturnsNull() {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(JSON_MAPPER.toJson(new SpotifySearchResult(
                            new SpotifySearchResultItem<>(singletonList(SpotifyTrack.builder()
                                    .id(SPOTIFY_ID)
                                    .name(SONG_NAME)
                                    .artists(singletonList(SpotifyArtist.builder().name("Wrong Artist").build()))
                                    .build())), null))));

            var track = apiService.searchTrack(ARTIST, SONG_NAME);

            assertThat(track).isEmpty();
        }

        @Test
        public void when200AndSongIsNotFound_ReturnsNull() {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(JSON_MAPPER.toJson(new SpotifySearchResult(new SpotifySearchResultItem<>(emptyList()), null))));

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
    class SearchArtist {

        @Test
        public void when200_ReturnsArtist() throws InterruptedException {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(JSON_MAPPER.toJson(new SpotifySearchResult(null,
                            new SpotifySearchResultItem<>(singletonList(
                                    SpotifyArtist.builder().name(ARTIST)
                                            .images(asList(new SpotifyImage("image1.jpg"), new SpotifyImage("image2.jpg")))
                                            .build()))))));

            var artist = apiService.searchArtist(ARTIST);

            assertThat(artist).isNotEmpty();
            assertThat(artist.get().getName()).isEqualTo(ARTIST);
            assertThat(artist.get().getImages()).isEqualTo(asList(new SpotifyImage("image1.jpg"), new SpotifyImage("image2.jpg")));

            RecordedRequest request = mockWebServer.takeRequest();
            assertThat(request.getMethod()).isEqualTo("GET");
            assertThat(requireNonNull(request.getRequestUrl()).encodedPath()).isEqualTo("/v1/search");
            assertThat(request.getRequestUrl().queryParameter("q"))
                    .isEqualTo("artist:" + ARTIST);
            assertThat(request.getRequestUrl().queryParameter("type")).isEqualTo("artist");
            assertThat(request.getRequestUrl().queryParameter("limit")).isEqualTo("1");
        }

        @Test
        public void when200AndArtistIsNotFound_ReturnsNull() {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(JSON_MAPPER.toJson(new SpotifySearchResult(null, new SpotifySearchResultItem<>(emptyList())))));

            var artist = apiService.searchArtist(ARTIST);

            assertThat(artist).isEmpty();
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

        @Test
        public void when200_ReturnsUserId() throws InterruptedException {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(JSON_MAPPER.toJson(new SpotifyUserProfile(USER_ID))));

            var userId = apiService.getUserId(AUTHORIZATION_HEADER);

            assertThat(userId).isEqualTo(USER_ID);

            RecordedRequest request = mockWebServer.takeRequest();
            assertThat(request.getMethod()).isEqualTo("GET");
            assertThat(request.getHeaders().get("Authorization")).isEqualTo(AUTHORIZATION_HEADER);
            assertThat(requireNonNull(request.getRequestUrl()).encodedPath()).isEqualTo("/v1/me");
        }

        @Test
        public void when401_throwsMusicPlatformAuthException() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(UNAUTHORIZED.value()));

            assertThrows(MusicPlatformAuthException.class, () -> apiService.getUserId(AUTHORIZATION_HEADER));
        }
    }

    @Nested
    class CreatePlaylist {
        private static final String USER_ID = "spotifyuserid";

        @Test
        public void when200AndPlaylistCreated_ReturnsPlaylist() throws InterruptedException {
            String playlistId = "playlist123";
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(OK.value())
                    .setBody(JSON_MAPPER.toJson(new SpotifyPlaylist(playlistId))));

            var playlist = apiService.createPlaylist(USER_ID,
                    new CreatePlaylistRequest("playlistName", "playlistDescription", false),
                    AUTHORIZATION_HEADER);

            assertThat(playlist.id()).isEqualTo(playlistId);

            RecordedRequest request = mockWebServer.takeRequest();
            assertThat(request.getMethod()).isEqualTo("POST");
            assertThat(request.getHeaders().get("Authorization")).isEqualTo(AUTHORIZATION_HEADER);
            assertThat(requireNonNull(request.getRequestUrl()).encodedPath()).isEqualTo("/v1/users/" + USER_ID + "/playlists");

            var body = JSON_MAPPER.fromJson(
                    request.getBody().readString(Charset.defaultCharset()), CreatePlaylistRequest.class);
            assertThat(body.getName()).isEqualTo("playlistName");
            assertThat(body.getDescription()).isEqualTo("playlistDescription");
            assertThat(body.isPublic()).isFalse();
        }

        @Test
        public void when401_throwsMusicPlatformAuthException() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(UNAUTHORIZED.value()));

            assertThrows(MusicPlatformAuthException.class, () -> apiService.getUserId(AUTHORIZATION_HEADER));
        }
    }

    @Nested
    class AddSongsToPlaylist {

        private final static String PLAYLIST_ID = "playlistId";

        @Test
        public void shouldCallApiWithFormattedUris() throws InterruptedException {
            mockWebServer.enqueue(new MockResponse().setResponseCode(CREATED.value()));

            apiService.addSongsToPlaylist(PLAYLIST_ID, asList("spt1", "spt2", "spt3"), AUTHORIZATION_HEADER);

            RecordedRequest request = mockWebServer.takeRequest();
            assertThat(request.getMethod()).isEqualTo("PUT");
            assertThat(request.getHeaders().get("Authorization")).isEqualTo(AUTHORIZATION_HEADER);
            assertThat(requireNonNull(request.getRequestUrl()).encodedPath()).isEqualTo("/v1/playlists/" + PLAYLIST_ID + "/tracks");
            assertThat(request.getRequestUrl().queryParameter("uris"))
                    .isEqualTo("spotify:track:spt1,spotify:track:spt2,spotify:track:spt3");
        }

        @Test
        public void when401_throwsMusicPlatformAuthException() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(UNAUTHORIZED.value()));

            assertThrows(MusicPlatformAuthException.class,
                    () ->apiService.addSongsToPlaylist(PLAYLIST_ID, asList("spt1", "spt2", "spt3"), AUTHORIZATION_HEADER));
        }
    }

}