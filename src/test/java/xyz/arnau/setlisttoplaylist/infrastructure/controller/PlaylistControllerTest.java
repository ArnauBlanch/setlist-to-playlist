package xyz.arnau.setlisttoplaylist.infrastructure.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.arnau.setlisttoplaylist.application.PlaylistService;
import xyz.arnau.setlisttoplaylist.domain.MusicPlatformAuthException;
import xyz.arnau.setlisttoplaylist.domain.Playlist;
import xyz.arnau.setlisttoplaylist.domain.SetlistNotFoundException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaylistControllerTest {

    @Mock
    private PlaylistService playlistService;

    @InjectMocks
    private PlaylistController playlistController;

    @BeforeEach
    void setUp() {
        standaloneSetup(playlistController);
    }

    @Nested
    class CreatePlaylistFromSetlist {
        private static final String AUTHORIZATION_HEADER = "Bearer user-token";

        @Test
        public void whenSetlistIsFound_shouldReturnCreated() {
            when(playlistService.createFromSetlist("abc12345", true, AUTHORIZATION_HEADER)).thenReturn(new Playlist("12345"));

            given().
                    header("Authorization", AUTHORIZATION_HEADER).
                    queryParam("isPublic", "true").
            when().
                    post("/playlists/abc12345").
            then().
                    statusCode(201).
                    body("id", equalTo("12345"));

            verify(playlistService).createFromSetlist("abc12345", true, AUTHORIZATION_HEADER);
        }

        @Test
        public void whenSetlistIsNotFound_shouldReturnNotFound() {
            when(playlistService.createFromSetlist("abc12345", true, AUTHORIZATION_HEADER)).thenThrow(SetlistNotFoundException.class);

            given().
                    header("Authorization", AUTHORIZATION_HEADER).
                    queryParam("isPublic", "true").
            when().
                    post("/playlists/abc12345").
            then().
                    statusCode(404);
        }

        @Test
        public void whenAuthorizationHeaderIsMissing_shouldReturnUnauthorized() {
            given().
                    queryParam("isPublic", "true").
            when().
                    post("/playlists/abc12345").
            then().
                    statusCode(401);
        }

        @Test
        public void whenThereIsAMusicPlatformAuthError_shouldReturnUnauthorized() {
            when(playlistService.createFromSetlist("abc12345", true, AUTHORIZATION_HEADER)).thenThrow(MusicPlatformAuthException.class);

            given().
                    header("Authorization", AUTHORIZATION_HEADER).
                    queryParam("isPublic", "true").
                    when().
                    post("/playlists/abc12345").
                    then().
                    statusCode(401);
        }
    }

    @Nested
    class GetPlaylistCover {

        @Test
        public void whenSetlistIsFound_shouldReturnCoverImage() {
            when(playlistService.getCoverImage("abc12345")).thenReturn(new byte[] {});

            given().
            when().
                    get("/playlists/abc12345/cover").
            then().
                    statusCode(200).
                    contentType("image/jpeg");
        }

        @Test
        public void whenSetlistIsNotFound_shouldReturnNotFound() {
            when(playlistService.getCoverImage("abc12345")).thenThrow(SetlistNotFoundException.class);

            given().
                    when().
                    get("/playlists/abc12345/cover").
                    then().
                    statusCode(404);
        }
    }
}