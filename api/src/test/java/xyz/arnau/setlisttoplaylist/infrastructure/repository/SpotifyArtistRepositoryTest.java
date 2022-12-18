package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyArtistRepository;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpotifyArtistRepositoryTest {
    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8081).dynamicHttpsPort())
            .build();

    @Autowired
    private SpotifyArtistRepository spotifyArtistRepository;

    @Nested
    class GetTopArtists {
        @Test
        public void shouldReturnTopArtists() {
            var topArtists = spotifyArtistRepository.getTopArtists();

            assertThat(topArtists).isEqualTo(asList(
                    Artist.builder().name("Bizarrap").imageUrl("https://i.scdn.co/image/ab6761610000e5eb12085fdb28f314e01ef73a19").build(),
                    Artist.builder().name("Quevedo").imageUrl("https://i.scdn.co/image/ab6761610000e5ebee2ddb7f81a2e8b76b629fbc").build(),
                    Artist.builder().name("Lady Gaga").imageUrl("https://i.scdn.co/image/ab6761610000e5ebc8d3d98a1bccbe71393dbfbf").build(),
                    Artist.builder().name("ROSALÍA").imageUrl("https://i.scdn.co/image/ab6761610000e5ebd7bb678bef6d2f26110cae49").build(),
                    Artist.builder().name("Arctic Monkeys").imageUrl("https://i.scdn.co/image/ab6761610000e5eb7da39dea0a72f581535fb11f").build(),
                    Artist.builder().name("Bad Bunny").imageUrl("https://i.scdn.co/image/ab6761610000e5eb8ee9a6f54dcbd4bc95126b14").build()
            ));
        }
    }
}