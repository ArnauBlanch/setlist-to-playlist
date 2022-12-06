package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.arnau.setlisttoplaylist.domain.CreatePlaylistCommand;
import xyz.arnau.setlisttoplaylist.domain.Playlist;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@WireMockTest(httpPort = 8081)
class SpotifyPlaylistRepositoryTest {

    @Autowired
    private SpotifyPlaylistRepository spotifyPlaylistRepository;

    @Test
    public void shouldCreatePlaylist() {
        CreatePlaylistCommand command = CreatePlaylistCommand.builder()
                .name("Manel at Plaça Corsini - September 18, 2022")
                .description("Setlist for Manel concert at Plaça Corsini (Tarragona, ES) on September 18, 2022.")
                .isPublic(false)
                .build();
        Playlist playlist = spotifyPlaylistRepository.create(command, "Bearer SPOTIFY_USER_TOKEN");

        assertThat(playlist.id()).isEqualTo("0a6xVqBCrxn8Ug0Wrudv9J");
    }
}