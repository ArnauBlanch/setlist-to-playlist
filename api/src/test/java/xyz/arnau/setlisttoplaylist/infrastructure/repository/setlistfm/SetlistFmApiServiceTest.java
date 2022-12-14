package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.*;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public class SetlistFmApiServiceTest {

    public static final Gson JSON_MAPPER = new GsonBuilder().create();

    private final MockWebServer mockWebServer = new MockWebServer();
    private final SetlistFmApi setlistFmApi = new Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SetlistFmApi.class);

    private final SetlistFmApiService apiService = new SetlistFmApiService(setlistFmApi);

    @Nested
    class GetSetlist {
        @Test
        public void whenSetlistIsFound_returnsSetlist() throws InterruptedException {
            var setlistResponse = aSetlist();
            enqueueOkResponse(setlistResponse);

            Optional<SetlistFmSetlist> setlist = apiService.getSetlist("12345");

            assertThat(setlist).isPresent();
            assertThat(setlist.get()).isEqualTo(setlistResponse);

            RecordedRequest request = mockWebServer.takeRequest();
            assertThat(request.getMethod()).isEqualTo("GET");
            assertThat(requireNonNull(request.getRequestUrl()).encodedPath()).isEqualTo("/rest/1.0/setlist/12345");
        }

        @Test
        public void whenSetlistIsNotFound_returnsEmpty() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(NOT_FOUND.value()));

            Optional<SetlistFmSetlist> setlist = apiService.getSetlist("12345");

            assertThat(setlist).isEmpty();
        }
    }

    @Nested
    class SearchArtists {

        @Test
        public void whenArtistsAreFound_returnsArtists() throws InterruptedException {
            enqueueOkResponse(new SetlistFmArtistSearchResult(asList(
                    SetlistFmArtist.builder().name("Arctic Monkeys").build(),
                    SetlistFmArtist.builder().name("Antarctic Monkeys").build()
            )));

            List<SetlistFmArtist> artists = apiService.searchArtists("Monkeys");

            assertThat(artists).hasSize(2);
            assertThat(artists.stream().map(SetlistFmArtist::getName).collect(toList()))
                    .containsExactly("Arctic Monkeys", "Antarctic Monkeys");

            RecordedRequest request = mockWebServer.takeRequest();
            assertThat(request.getMethod()).isEqualTo("GET");
            assertThat(requireNonNull(request.getRequestUrl()).encodedPath()).isEqualTo("/rest/1.0/search/artists");
            assertThat(request.getRequestUrl().queryParameter("artistName")).isEqualTo("Monkeys");
            assertThat(request.getRequestUrl().queryParameter("sort")).isEqualTo("relevance");
        }
        @Test
        public void whenNoArtistFound_returnsEmpty() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(NOT_FOUND.value()));

            List<SetlistFmArtist> artists = apiService.searchArtists("unknown");

            assertThat(artists).isEmpty();
        }


    }
    @Nested
    class GetArtist {

        @Test
        public void whenArtistIsFound_returnsArtist() throws InterruptedException {
            SetlistFmArtist artistResponse = SetlistFmArtist.builder()
                    .id("4e7209ee-ef02-4cb7-bdff-815b0473c27c")
                    .name("Manel")
                    .disambiguation("Manel")
                    .sortName("Manel")
                    .build();
            enqueueOkResponse(artistResponse);

            Optional<SetlistFmArtist> artist = apiService.getArtist("4e7209ee-ef02-4cb7-bdff-815b0473c27c");

            assertThat(artist).isPresent();
            assertThat(artist.get()).isEqualTo(artistResponse);

            RecordedRequest request = mockWebServer.takeRequest();
            assertThat(request.getMethod()).isEqualTo("GET");
            assertThat(requireNonNull(request.getRequestUrl()).encodedPath()).isEqualTo("/rest/1.0/artist/4e7209ee-ef02-4cb7-bdff-815b0473c27c");
        }
        @Test
        public void whenArtistIsNotFound_returnsEmpty() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(NOT_FOUND.value()));

            Optional<SetlistFmArtist> artist = apiService.getArtist("4e7209ee-ef02-4cb7-bdff-815b0473c27c");

            assertThat(artist).isEmpty();
        }

    }
    @Nested
    class GetArtistSetlists {

        @Test
        public void whenArtistSetlistsAreFound_returnsArtist() throws InterruptedException {
            var response = SetlistFmArtistSetlistsPage.builder()
                    .setlists(singletonList(aSetlist()))
                    .page(1).itemsPerPage(20).total(10).build();
            enqueueOkResponse(response);

            var setlistsPage = apiService.getArtistSetlists("4e7209ee-ef02-4cb7-bdff-815b0473c27c", 1);

            assertThat(setlistsPage).isPresent();
            assertThat(setlistsPage.get()).isEqualTo(response);

            RecordedRequest request = mockWebServer.takeRequest();
            assertThat(request.getMethod()).isEqualTo("GET");
            assertThat(requireNonNull(request.getRequestUrl()).encodedPath()).isEqualTo("/rest/1.0/artist/4e7209ee-ef02-4cb7-bdff-815b0473c27c/setlists");
            assertThat(requireNonNull(request.getRequestUrl()).queryParameter("p")).isEqualTo("1");
        }
        @Test
        public void whenNoArtistSetlistsAreFound_returnsEmpty() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(NOT_FOUND.value()));

            var setlistsPage = apiService.getArtistSetlists("4e7209ee-ef02-4cb7-bdff-815b0473c27c", 1);

            assertThat(setlistsPage).isEmpty();
        }

    }
    private static SetlistFmSetlist aSetlist() {
        return SetlistFmSetlist.builder()
                .id("12345")
                .eventDate("2022-01-01")
                .artist(SetlistFmArtist.builder().name("Manel").build())
                .venue(new SetlistFmVenue("Apolo",
                        new SetlistFmCity("Barcelona", null, "Catalonia",
                                new SetlistFmCountry("ES", "Spain"))))
                .sets(new SetlistFmSets(singletonList(new SetlistFmSet(asList(
                        SetlistFmSong.builder().name("Benvolgut").build(),
                        SetlistFmSong.builder().name("Per la bona gent").build()
                ))))).build();
    }

    private void enqueueOkResponse(Object body) {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(OK.value())
                .setBody(JSON_MAPPER.toJson(body)));
    }
}
