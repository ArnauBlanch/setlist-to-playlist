package xyz.arnau.setlisttoplaylist.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.domain.*;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {

    private static final String SETLIST_ID = "abc12345";
    private static final String USER_TOKEN = "user_token";

    @Mock
    private SetlistService setlistService;
    @Mock
    private PlaylistRepository playlistRepository;
    @Mock
    private PlaylistImageGenerator playlistImageGenerator;

    @InjectMocks
    private PlaylistService playlistService;

    @Captor
    private ArgumentCaptor<CreatePlaylistCommand> createCommandCaptor;

    @Test
    public void whenSetlistIsFound_shouldCreatePlaylist() {
        var playlist = new Playlist("12345");
        var setlist = aSetlist();
        var imageBytes = "image".getBytes();

        when(setlistService.getSetlist(SETLIST_ID)).thenReturn(Optional.of(setlist));
        when(playlistRepository.create(createCommandCaptor.capture(), eq(USER_TOKEN))).thenReturn(playlist);
        when(playlistImageGenerator.generateImage(setlist)).thenReturn(imageBytes);

        var result = playlistService.createFromSetlist(SETLIST_ID, false, USER_TOKEN);

        assertThat(result).isEqualTo(playlist);

        CreatePlaylistCommand command = createCommandCaptor.getValue();
        assertThat(command.name()).isEqualTo("The Strokes at Primavera Sound - June 10, 2022");
        assertThat(command.description())
                .isEqualTo("Setlist for The Strokes concert at Primavera Sound (Barcelona, ES) on June 10, 2022.");
        assertThat(command.isPublic()).isEqualTo(false);
        assertThat(command.songIds()).containsExactly("spt1", "spt2");
        assertThat(command.coverImage()).isEqualTo(imageBytes);
    }

    @Test
    public void whenSetlistIsNotFound_shouldThrowSetlistNotFoundException() {
        when(setlistService.getSetlist(SETLIST_ID)).thenReturn(Optional.empty());

        assertThrows(SetlistNotFoundException.class, () -> playlistService.createFromSetlist(SETLIST_ID, false, USER_TOKEN));

        verify(playlistRepository, never()).create(any(), anyString());
    }

    @Test
    public void whenSetlistIsEmpty_shouldThrowSetlistNotFoundException() {
        when(setlistService.getSetlist(SETLIST_ID)).thenReturn(Optional.of(Setlist.builder().songs(emptyList()).build()));

        assertThrows(SetlistNotFoundException.class, () -> playlistService.createFromSetlist(SETLIST_ID, false, USER_TOKEN));

        verify(playlistRepository, never()).create(any(), anyString());
    }

    @Test
    public void whenCantCreatePlaylistBecauseOfAuthError_shouldThrowException() {
        var setlist = aSetlist();

        when(setlistService.getSetlist(SETLIST_ID)).thenReturn(Optional.of(setlist));
        when(playlistService.createFromSetlist(SETLIST_ID, false, USER_TOKEN)).thenThrow(MusicPlatformAuthException.class);

        assertThrows(MusicPlatformAuthException.class, () -> playlistService.createFromSetlist(SETLIST_ID, false, USER_TOKEN));
    }

    private Setlist aSetlist() {
        return Setlist.builder()
                .artist(Artist.builder()
                        .name("The Strokes")
                        .build())
                .date(LocalDate.of(2022, 6, 10))
                .venue(Venue.builder()
                        .name("Primavera Sound")
                        .city("Barcelona")
                        .countryCode("ES")
                        .build())
                .songs(asList(
                        Song.builder().id("spt1").name("Reptilia").build(),
                        Song.builder().id("spt2").name("Under Cover the Darkness").build(),
                        Song.builder().id(null).name("UNKNOWN").build()
                ))
                .build();
    }
}