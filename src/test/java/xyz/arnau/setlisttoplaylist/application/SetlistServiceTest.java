package xyz.arnau.setlisttoplaylist.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.domain.*;

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

    @Test
    public void whenSetlistIsFound_ReturnsSetlist() {
        when(setlistRepository.getSetlist("abc12345"))
                .thenReturn(Optional.of(
                        Setlist.builder()
                                .artist(Artist.builder().name("The Strokes").build())
                                .songs(singletonList(Song.builder().name("Last Nite").build()))
                                .build()));

        Setlist setlist = setlistService.getSetlist("abc12345");

        assertThat(setlist).isNotNull();
        assertThat(setlist.artist()).isNotNull();
        assertThat(setlist.artist().name()).isEqualTo("The Strokes");
    }

    @Test
    public void whenSetlistIsNotFound_ThrowsSetlistNotFoundException() {
        when(setlistRepository.getSetlist("abc12345")).thenReturn(Optional.empty());

        assertThrows(SetlistNotFoundException.class, () -> setlistService.getSetlist("abc12345"));
    }


    @Test
    public void whenSetlistHasNoSongs_ThrowsSetlistNotFoundException() {
        when(setlistRepository.getSetlist("abc12345"))
                .thenReturn(Optional.of(
                        Setlist.builder()
                                .artist(Artist.builder().name("The Strokes").build())
                                .songs(emptyList())
                                .build()));

        assertThrows(SetlistNotFoundException.class, () -> setlistService.getSetlist("abc12345"));
    }
}