package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.application.ArtistService;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArtistControllerTest {

    @Mock
    private ArtistService artistService;

    @InjectMocks
    private ArtistController artistController;


    @BeforeEach
    void setUp() {
        standaloneSetup(artistController);
    }

    @Nested
    class GetTopArtists {
        @Test
        public void shouldReturnOk() {
            when(artistService.getTopArtists()).thenReturn(asList(
                    Artist.builder().name("Arctic Monkeys").imageUrl("url1").build(),
                    Artist.builder().name("Tame Impala").imageUrl("url2").build(),
                    Artist.builder().name("The Strokes").imageUrl("url3").build()
            ));

            given().
                    queryParam("count", 2).
            when().
                    get("/artists/top").
            then().
                    statusCode(200).
                    body("$", hasSize(2));
        }
    }

    @Nested
    class GetArtists {
        @Test
        public void whenArtistsFound_shouldOkWithArtists() {
            when(artistService.getAllByName("Manel")).thenReturn(asList(
                    Artist.builder().name("Manel").imageUrl("img1").build(),
                    Artist.builder().name("Fake Manel").imageUrl("img2").build()
            ));

            given().
                    queryParam("name", "Manel").
            when().
                    get("/artists").
            then().
                    statusCode(200).
                    body("$", hasSize(2));

        }
    }
}