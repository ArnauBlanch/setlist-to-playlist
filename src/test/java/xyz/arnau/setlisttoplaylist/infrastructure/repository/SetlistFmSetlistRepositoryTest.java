package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.arnau.setlisttoplaylist.domain.Artist;
import xyz.arnau.setlisttoplaylist.domain.Setlist;
import xyz.arnau.setlisttoplaylist.domain.Song;
import xyz.arnau.setlisttoplaylist.domain.Venue;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@WireMockTest(httpPort = 8081)
class SetlistFmSetlistRepositoryTest {

    @Autowired
    private SetlistFmSetlistRepository setlistRepository;

    @Test
    public void shouldReturnSetlist() {

        Optional<Setlist> setlist = setlistRepository.getSetlist("abc12345");

        assertThat(setlist).isNotEmpty();
        assertThat(setlist.get().date()).isEqualTo("2022-09-18");
        assertThat(setlist.get().artist()).isEqualTo(new Artist("Manel"));
        assertThat(setlist.get().venue())
                .isEqualTo(new Venue("Plaça Corsini", "Tarragona", "Spain", "ES"));
        assertThat(setlist.get().songs()).isEqualTo(asList(
                new Song("En la que el Bernat se't troba", null),
                new Song("Per la bona gent", null),
                new Song("Els entusiasmats", null),
                new Song("Tipus suite", null),
                new Song("Criticarem les noves modes de pentinats", null),
                new Song("Aquí tens el meu braç", null),
                new Song("La jungla", null),
                new Song("Les estrelles", null),
                new Song("Formigues", null),
                new Song("Captatio benevolentiae", null),
                new Song("Jo competeixo", null),
                new Song("La cançó del soldadet", null),
                new Song("Sabotatge", null),
                new Song("Boomerang", null),
                new Song("Teresa Rampell", null),
                new Song("Boy Band", null),
                new Song("La gent normal", "Pulp"),
                new Song("Al mar!", null),
                new Song("Benvolgut", null)
        ));
    }
}