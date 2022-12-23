package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.arnau.setlisttoplaylist.domain.dto.CreatePlaylistCommand;
import xyz.arnau.setlisttoplaylist.domain.entities.Playlist;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpotifyPlaylistRepositoryIntegrationTest {
    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8081).dynamicHttpsPort())
            .build();

    @Autowired
    private SpotifyPlaylistRepository playlistRepository;

    @Test
    public void shouldCreatePlaylist() {
        CreatePlaylistCommand command = CreatePlaylistCommand.builder()
                .name("Manel at Plaça Corsini - September 18, 2022")
                .description("Setlist for Manel concert at Plaça Corsini (Tarragona, ES) on September 18, 2022.")
                .isPublic(false)
                .songIds(asList("6H86gna5KDoPurwLxb6pIV", "4lKwqIEmnm0wsRLOuwUMLv", "4KQPAGQNStZaWiewr83fwM",
                        "6ADbZPiWZNsaCiIvsg5iq6", "6lSJZiZqWU8Qt1fJVeFZEv"))
                .coverImage("image".getBytes())
                .build();
        Playlist playlist = playlistRepository.create(command, "Bearer SPOTIFY_USER_TOKEN");

        assertThat(playlist.id()).isEqualTo("0a6xVqBCrxn8Ug0Wrudv9J");
    }
}