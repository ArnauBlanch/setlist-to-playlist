package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.application.ArtistService;
import xyz.arnau.setlisttoplaylist.application.SetlistService;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.entities.BasicSetlist;
import xyz.arnau.setlisttoplaylist.domain.entities.PagedList;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArtistControllerTest {
    public static final String ARTIST_ID = "4e7209ee-ef02-4cb7-bdff-815b0473c27c";

    @Mock
    private ArtistService artistService;
    @Mock
    private SetlistService setlistService;

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
        public void whenArtistsFound_shouldReturnOkWithArtists() {
            when(artistService.searchByName("Manel")).thenReturn(asList(
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
    @Nested
    class GetArtist {
        @Test
        public void whenArtistFound_shouldReturnOkWithArtistInfo() {
            when(artistService.getById(ARTIST_ID)).thenReturn(Artist.builder().name("Manel").build());
            given().
            when().
                    get("/artists/" + ARTIST_ID).
                    then().
                    statusCode(200)
                    .body("name", equalTo("Manel"));
        }
    }

    @Nested
    class GetArtistSetlists {
        @Test
        public void whenArtistFound_shouldReturnOkWithSetlists() {
            when(setlistService.getByArtistId(ARTIST_ID, 1))
                    .thenReturn(PagedList.<BasicSetlist>builder()
                            .items(asList(
                                    BasicSetlist.builder().id("53be1b39").build(),
                                    BasicSetlist.builder().id("4bbe1b46").build(),
                                    BasicSetlist.builder().id("73b07e29").build()))
                            .itemsPerPage(20)
                            .page(1)
                            .totalItems(1)
                            .build());

            given().
                    queryParam("page", 1).
            when().
                    get("/artists/" + ARTIST_ID + "/setlists").
            then().
                    statusCode(200)
                    .body("setlists.id", equalTo(asList("53be1b39", "4bbe1b46", "73b07e29")))
                    .body("page", equalTo(1))
                    .body("totalItems", equalTo(1))
                    .body("itemsPerPage", equalTo(20));
        }
    }
}
