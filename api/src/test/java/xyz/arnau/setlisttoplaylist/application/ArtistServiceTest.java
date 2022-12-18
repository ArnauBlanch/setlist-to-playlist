package xyz.arnau.setlisttoplaylist.application;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.ports.ArtistRepository;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {
    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    @Nested
    class GetTopArtists {
        @Test
        public void shouldReturnTopArtists() {
            var artists = asList(
                    Artist.builder().name("Arctic Monkeys").imageUrl("url1").build(),
                    Artist.builder().name("Tame Impala").imageUrl("url2").build(),
                    Artist.builder().name("The Strokes").imageUrl("url3").build()
            );
            when(artistRepository.getTopArtists()).thenReturn(artists);

            assertThat(artistService.getTopArtists()).isEqualTo(artists);
        }
    }
}