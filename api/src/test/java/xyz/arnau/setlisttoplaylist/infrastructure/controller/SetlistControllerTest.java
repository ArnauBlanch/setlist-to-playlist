package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.application.SetlistService;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.domain.exceptions.SetlistNotFoundException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SetlistControllerTest {

    @Mock
    private SetlistService setlistService;

    @InjectMocks
    private SetlistController setlistController;

    @BeforeEach
    void setUp() {
        standaloneSetup(setlistController);
    }

    @Nested
    class GetSetlist {
        @Test
        public void whenSetlistIsFound_shouldReturnOk() {
            var setlist = Setlist.builder().artist(Artist.builder().name("The Strokes").build()).build();

            when(setlistService.getById("abc12345")).thenReturn(setlist);

            when().
                    get("/setlists/abc12345").
            then().
                    statusCode(200).
                    body("artist.name", equalTo("The Strokes"));
        }

        @Test
        public void whenSetlistIsNotFound_shouldReturnNotFound() {
            when(setlistService.getById("not-found")).thenThrow(SetlistNotFoundException.class);

            when().
                    get("/setlists/not-found").
                    then().
                    statusCode(404);
        }
    }
}