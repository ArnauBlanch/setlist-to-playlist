package xyz.arnau.setlisttoplaylist;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static wiremock.org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static wiremock.org.eclipse.jetty.http.HttpStatus.OK_200;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WireMockTest(httpPort = 8081)
class SetlistInfoToPlaylistApplicationTests {

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	void givenASetlistId_shouldReturnSetlist() {
		given()
		.when()
				.get("/setlists/abc12345")
		.then()
				.statusCode(OK_200)
				.body("artist.name", equalTo("Manel"));

	}

	@Test
	void givenASetlistId_shouldCreateSpotifyPlaylist() {
		given()
				.header("Authorization", "Bearer SPOTIFY_USER_TOKEN")
		.when()
				.post("/setlists/abc12345/playlist")
		.then()
				.statusCode(CREATED_201);
	}

}
