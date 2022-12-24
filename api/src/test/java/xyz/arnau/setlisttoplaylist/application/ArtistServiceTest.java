package xyz.arnau.setlisttoplaylist.application;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.exceptions.ArtistNotFoundException;
import xyz.arnau.setlisttoplaylist.domain.ports.ArtistRepository;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
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

            var topArtists = artistService.getTopArtists();

            assertThat(topArtists).isEqualTo(artists);
        }
    }

    @Nested
    class SearchByName {
        @Test
        public void givenAnArtistName_shouldReturnArtists() {
            var artists = asList(
                    Artist.builder().name("Manel").imageUrl("url1").build(),
                    Artist.builder().name("Fake Manel").imageUrl("url2").build()
            );
            when(artistRepository.searchByName("Manel")).thenReturn(artists);

            var artistsFound = artistService.searchByName("Manel");

            assertThat(artistsFound).isEqualTo(artists);
        }
    }

    @Nested
    class GetById {
        private static final String ARTIST_ID = "d15721d8-56b4-453d-b506-fc915b14cba2";

        @Test
        public void whenArtistIsFound_shouldReturnArtist() {
            var artist = Artist.builder().id(ARTIST_ID).name("Manel").imageUrl("url1").build();
            when(artistRepository.getById(ARTIST_ID)).thenReturn(Optional.of(artist));

            var artistFound = artistService.getById(ARTIST_ID);

            assertThat(artistFound).isEqualTo(artist);
        }

        @Test
        public void whenArtistIsNotFound_shouldThrowArtistNotFoundException() {
            when(artistRepository.getById("not-found")).thenReturn(Optional.empty());

            assertThrows(ArtistNotFoundException.class, () -> artistService.getById("not-found"));
        }
    }
}