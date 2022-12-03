package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wiremock.org.eclipse.jetty.http.HttpStatus;
import xyz.arnau.setlisttoplaylist.application.SetlistService;
import xyz.arnau.setlisttoplaylist.domain.ArtistInfo;
import xyz.arnau.setlisttoplaylist.domain.Setlist;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SetlistInfoControllerTest {

    @Mock
    private SetlistService setlistService;

    @InjectMocks
    private SetlistController setlistController;

    @Test
    public void whenSetlistDoesNotExist_shouldReturnNotFound() {
        when(setlistService.getSetlist("not-found")).thenReturn(Optional.empty());

        var response = setlistController.getSetlist("not-found");

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND_404);
    }

    @Test
    public void whenSetlistExists_shouldReturnOk() {
        var setlist = new Setlist(new ArtistInfo("The Strokes"));

        when(setlistService.getSetlist("not-found")).thenReturn(Optional.of(setlist));

        var response = setlistController.getSetlist("not-found");

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK_200);
        assertThat(response.getBody()).isEqualTo(setlist);
    }
}