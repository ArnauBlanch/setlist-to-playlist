package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.arnau.setlisttoplaylist.domain.Artist;
import xyz.arnau.setlisttoplaylist.domain.Setlist;
import xyz.arnau.setlisttoplaylist.domain.Song;
import xyz.arnau.setlisttoplaylist.domain.Venue;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmSetlistRepository;

import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
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
        assertThat(setlist.get().artist()).isEqualTo(new Artist("Manel", "https://i.scdn.co/image/ab6761610000e5ebf03cdcbdda43b390cf876a6a"));
        assertThat(setlist.get().venue())
                .isEqualTo(new Venue("Plaça Corsini", "Tarragona", "Spain", "ES"));
        assertThat(setlist.get().songs().stream().map(Song::id).collect(toList()))
                .isEqualTo(asList("6H86gna5KDoPurwLxb6pIV", "4lKwqIEmnm0wsRLOuwUMLv", null,
                        "4KQPAGQNStZaWiewr83fwM", "6ADbZPiWZNsaCiIvsg5iq6", "6lSJZiZqWU8Qt1fJVeFZEv"));
    }
}