package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import com.google.gson.GsonBuilder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.ArtistItem;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SearchResult;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.TrackItem;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.TracksSearchResult;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static wiremock.org.eclipse.jetty.http.HttpStatus.OK_200;

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


    @Test
    public void when200AndSongMatches_ReturnsSong() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(OK_200)
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
                .setResponseCode(OK_200)
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
                .setResponseCode(OK_200)
                .setBody(toJson(searchResult(null))));

        var track = apiService.searchTrack(ARTIST, SONG_NAME);

        assertThat(track).isEmpty();
    }

    private String toJson(SearchResult result) {
        return new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create()
                .toJson(result);
    }

    private SearchResult searchResult(TrackItem track) {
        return new SearchResult(new TracksSearchResult(
                track != null ? singletonList(track) : emptyList()));
    }
}