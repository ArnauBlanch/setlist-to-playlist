package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wiremock.org.eclipse.jetty.http.HttpStatus;
import xyz.arnau.setlisttoplaylist.application.PlaylistService;
import xyz.arnau.setlisttoplaylist.application.SetlistService;
import xyz.arnau.setlisttoplaylist.domain.Artist;
import xyz.arnau.setlisttoplaylist.domain.Playlist;
import xyz.arnau.setlisttoplaylist.domain.Setlist;
import xyz.arnau.setlisttoplaylist.domain.SetlistNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaylistControllerTest {

    @Mock
    private PlaylistService playlistService;

    @InjectMocks
    private PlaylistController playlistController;

    @Nested
    class CreatePlaylistFromSetlist {
        private static final String AUTHORIZATION_HEADER = "Bearer user-token";

        @Test
        public void whenSetlistIsFound_shouldReturnCreated() {
            when(playlistService.createFromSetlist("abc12345", true, AUTHORIZATION_HEADER)).thenReturn(new Playlist("12345"));

            var response = playlistController.createPlaylistFromSetlist("abc12345", true, AUTHORIZATION_HEADER);

            assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED_201);

            verify(playlistService).createFromSetlist("abc12345", true, AUTHORIZATION_HEADER);
        }

        @Test
        public void whenSetlistIsNotFound_shouldReturnNotFound() {
            when(playlistService.createFromSetlist("abc12345", true, AUTHORIZATION_HEADER)).thenThrow(SetlistNotFoundException.class);

            var response = playlistController.createPlaylistFromSetlist("abc12345", true, AUTHORIZATION_HEADER);

            assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND_404);
        }
    }

    @Nested
    class GetPlaylistCover {

        @Test
        public void whenSetlistIsFound_shouldReturnCoverImage() {
            when(playlistService.getCoverImage("abc12345")).thenReturn(null);

            var response = playlistController.getPlaylistCover("abc12345");

            assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK_200);

        }

        @Test
        public void whenSetlistIsNotFound_shouldReturnNotFound() {
            when(playlistService.getCoverImage("abc12345")).thenThrow(SetlistNotFoundException.class);

            var response = playlistController.getPlaylistCover("abc12345");

            assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND_404);
        }
    }
}