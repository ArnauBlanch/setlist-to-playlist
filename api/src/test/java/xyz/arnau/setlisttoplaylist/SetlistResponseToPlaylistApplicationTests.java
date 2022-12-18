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
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static wiremock.org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static wiremock.org.eclipse.jetty.http.HttpStatus.OK_200;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WireMockTest(httpPort = 8081)
class SetlistResponseToPlaylistApplicationTests {

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	void givenASetlistId_shouldReturnSetlist() {
		given().
		when().
				get("/setlists/abc12345").
		then().
				statusCode(OK_200).
				body(
						"artist.name", equalTo("Manel"),
						"venue.name", equalTo("Plaça Corsini"),
						"venue.city", equalTo("Tarragona"),
						"venue.country", equalTo("Spain"),
						"venue.countryCode", equalTo("ES"),
						"date", equalTo("2022-09-18"),
						"songs.id", equalTo(asList(
								"6H86gna5KDoPurwLxb6pIV", "4lKwqIEmnm0wsRLOuwUMLv", null,
								"4KQPAGQNStZaWiewr83fwM", "6ADbZPiWZNsaCiIvsg5iq6", "6lSJZiZqWU8Qt1fJVeFZEv")));
	}

	@Test
	void givenASetlistId_shouldCreateSpotifyPlaylistAndAddSongs() {
		given().
				header("Authorization", "Bearer SPOTIFY_USER_TOKEN").
		when().
				post("/playlists/abc12345").
		then().
				statusCode(CREATED_201);
	}

	@Test
	void givenASetlistId_shouldReturnPlaylistCoverImage() {
		given().
		when().
				get("/playlists/abc12345/cover").
		then().
				statusCode(OK_200);
	}

	@Test
	void shouldReturnRandomTopArtists() {
		given().
				queryParam("count", 5).
		when().
				get("/artists/top").
		then().
				statusCode(OK_200);
	}

	@Test
	void givenAnArtistName_shouldReturnArtists() {
		given().
				queryParam("name", "Manel").
		when().
				get("/artists").
		then().
				statusCode(OK_200);

	}
}
