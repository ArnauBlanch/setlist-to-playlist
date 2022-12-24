package xyz.arnau.setlisttoplaylist.application;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.domain.entities.*;
import xyz.arnau.setlisttoplaylist.domain.exceptions.ArtistSetlistsNotFoundException;
import xyz.arnau.setlisttoplaylist.domain.exceptions.SetlistNotFoundException;
import xyz.arnau.setlisttoplaylist.domain.ports.SetlistRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SetlistServiceTest {

    @Mock
    private SetlistRepository setlistRepository;

    @InjectMocks
    private SetlistService setlistService;

    @Nested
    class GetById {
        @Test
        public void whenSetlistIsFound_ReturnsSetlist() {
            when(setlistRepository.getSetlist("abc12345"))
                    .thenReturn(Optional.of(
                            Setlist.builder()
                                    .artist(Artist.builder().name("The Strokes").build())
                                    .songs(singletonList(Song.builder().name("Last Nite").build()))
                                    .build()));

            Setlist setlist = setlistService.getById("abc12345");

            assertThat(setlist).isNotNull();
            assertThat(setlist.artist()).isNotNull();
            assertThat(setlist.artist().name()).isEqualTo("The Strokes");
        }

        @Test
        public void whenSetlistIsNotFound_ThrowsSetlistNotFoundException() {
            when(setlistRepository.getSetlist("abc12345")).thenReturn(Optional.empty());

            assertThrows(SetlistNotFoundException.class, () -> setlistService.getById("abc12345"));
        }

        @Test
        public void whenSetlistHasNoSongs_ThrowsSetlistNotFoundException() {
            when(setlistRepository.getSetlist("abc12345"))
                    .thenReturn(Optional.of(
                            Setlist.builder()
                                    .artist(Artist.builder().name("The Strokes").build())
                                    .songs(emptyList())
                                    .build()));

            assertThrows(SetlistNotFoundException.class, () -> setlistService.getById("abc12345"));
        }

    }

    @Nested
    class GetByArtistId {
        private static final String ARTIST_ID = "4e7209ee-ef02-4cb7-bdff-815b0473c27c";

        @Test
        public void whenSetlistsAreFound_ReturnsEmptyList() {
            PagedList<BasicSetlist> setlistList = PagedList.<BasicSetlist>builder()
                    .items(singletonList(BasicSetlist.builder().id("setlist1").numSongs(15).build()))
                    .page(1)
                    .totalItems(1)
                    .itemsPerPage(20)
                    .build();

            when(setlistRepository.getArtistSetlists(ARTIST_ID, 1)).thenReturn(Optional.of(setlistList));

            var result = setlistService.getByArtistId(ARTIST_ID, 1);

            assertThat(result).isEqualTo(setlistList);
        }

        @Test
        public void whenNoSetlistsAreFound_throwsArtistSetlistsNotFoundException() {
            when(setlistRepository.getArtistSetlists(ARTIST_ID, 1)).thenReturn(Optional.empty());

            assertThrows(ArtistSetlistsNotFoundException.class, () -> setlistService.getByArtistId(ARTIST_ID, 1));
        }
    }

}