package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.arnau.setlisttoplaylist.domain.Setlist;

import java.util.Optional;

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
        assertThat(setlist.get().artist().name()).isEqualTo("The Strokes");
    }
}