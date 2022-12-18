package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.domain.entities.Song;
import xyz.arnau.setlisttoplaylist.domain.entities.Venue;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmSetlistRepository;

import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SetlistFmSetlistRepositoryTest {
    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8081).dynamicHttpsPort())
            .build();

    @Autowired
    private SetlistFmSetlistRepository setlistRepository;


    @Nested
    class GetSetlist {
        @Test
        public void shouldReturnSetlist() {
            Optional<Setlist> setlist = setlistRepository.getSetlist("abc12345");

            assertThat(setlist).isNotEmpty();
            assertThat(setlist.get().date()).isEqualTo("2022-09-18");
            assertThat(setlist.get().artist()).isEqualTo(new Artist("Manel", "https://i.scdn.co/image/ab6761610000e5ebf03cdcbdda43b390cf876a6a"));
            assertThat(setlist.get().venue())
                    .isEqualTo(new Venue("Plaça Corsini", "Tarragona", "Spain", "ES"));
            assertThat(setlist.get().songs().stream().map(Song::id).collect(toList()))
                    .isEqualTo(asList("6H86gna5KDoPurwLxb6pIV", "4lKwqIEmnm0wsRLOuwUMLv", null,
                            "4KQPAGQNStZaWiewr83fwM", "6ADbZPiWZNsaCiIvsg5iq6", "6lSJZiZqWU8Qt1fJVeFZEv"));
        }
    }

    @Nested
    class GetArtistsByName {
        @Test
        public void whenResultsFound_shouldReturnArtists() {
            List<Artist> artists = setlistRepository.getArtistsByName("Manel");

            assertThat(artists).isEqualTo(singletonList(
                    Artist.builder().name("Manel").imageUrl("https://i.scdn.co/image/ab6761610000e5ebf03cdcbdda43b390cf876a6a").build()
            ));
        }

        @Test
        public void whenResultsNotFound_shouldReturnEmpty() {
            List<Artist> artists = setlistRepository.getArtistsByName("unknown");

            assertThat(artists).isEmpty();
        }
    }
}