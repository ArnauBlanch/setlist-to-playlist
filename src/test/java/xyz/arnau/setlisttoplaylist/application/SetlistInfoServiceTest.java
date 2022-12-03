package xyz.arnau.setlisttoplaylist.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.domain.Artist;
import xyz.arnau.setlisttoplaylist.domain.Setlist;
import xyz.arnau.setlisttoplaylist.domain.SetlistRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SetlistInfoServiceTest {

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
                                .build()));

        Optional<Setlist> setlist = setlistService.getSetlist("abc12345");

        assertThat(setlist).isNotEmpty();
        assertThat(setlist.get().artist()).isNotNull();
        assertThat(setlist.get().artist().name()).isEqualTo("The Strokes");
    }
}