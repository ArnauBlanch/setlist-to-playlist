package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.arnau.setlisttoplaylist.domain.entities.*;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpotifySetlistRepositoryIntegrationTest {
    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8081).dynamicHttpsPort())
            .build();

    @Autowired
    private SpotifySetlistRepository setlistRepository;


    @Nested
    class GetSetlist {
        @Test
        public void shouldReturnSetlist() {
            Optional<Setlist> setlist = setlistRepository.getSetlist("abc12345");

            assertThat(setlist).isNotEmpty();
            assertThat(setlist.get().date()).isEqualTo("2022-09-18");
            assertThat(setlist.get().artist()).isEqualTo(Artist.builder()
                    .id("4e7209ee-ef02-4cb7-bdff-815b0473c27c")
                    .musicPlatformId("40tHhop0T30DwienQBmTxb")
                    .name("Manel")
                    .imageUrl("https://i.scdn.co/image/ab6761610000e5ebf03cdcbdda43b390cf876a6a")
                    .build());
            assertThat(setlist.get().venue())
                    .isEqualTo(new Venue("Plaça Corsini", "Tarragona", "Spain", "ES"));
            assertThat(setlist.get().songs().stream().map(Song::musicPlatformId).collect(toList()))
                    .isEqualTo(asList("6H86gna5KDoPurwLxb6pIV", "4lKwqIEmnm0wsRLOuwUMLv", null,
                            "4KQPAGQNStZaWiewr83fwM", "6ADbZPiWZNsaCiIvsg5iq6", "6lSJZiZqWU8Qt1fJVeFZEv"));
        }
    }

    @Nested
    class GetArtistSetlists {
        @Test
        public void shouldReturnArtistSetlists() {
            var setlistsPage = setlistRepository.getArtistSetlists("4e7209ee-ef02-4cb7-bdff-815b0473c27c", 1);

            assertThat(setlistsPage).isNotEmpty();
            assertThat(setlistsPage.get().items().stream().map(BasicSetlist::id).collect(toList()))
                    .isEqualTo(asList("53be1b39", "4bbe1b46", "73b07e29"));
            assertThat(setlistsPage.get().items().stream().map(BasicSetlist::numSongs).collect(toList()))
                    .isEqualTo(asList(0, 0, 6));
            assertThat(setlistsPage.get().page()).isEqualTo(1);
            assertThat(setlistsPage.get().totalItems()).isEqualTo(168);
            assertThat(setlistsPage.get().itemsPerPage()).isEqualTo(20);
        }
    }
}