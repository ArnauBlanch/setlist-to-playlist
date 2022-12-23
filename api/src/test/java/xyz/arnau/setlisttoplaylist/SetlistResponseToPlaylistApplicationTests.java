package xyz.arnau.setlisttoplaylist;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import org.jetbrains.annotations.NotNull;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.boot.test.util.TestPropertyValues.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WireMockTest(httpPort = 8081)
@ContextConfiguration(initializers = SetlistResponseToPlaylistApplicationTests.Initializer.class)
class SetlistResponseToPlaylistApplicationTests {

	@LocalServerPort
	private int port;

	@ClassRule
	public static GenericContainer<?> chromeWebDriver = new GenericContainer<>(DockerImageName.parse("browserless/chrome"))
			.withExposedPorts(3000)
			.waitingFor(Wait.forHttp("/").forStatusCode(200));

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
				statusCode(OK.value()).
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
				statusCode(CREATED.value());
	}

	@Test
	void givenASetlistId_shouldReturnPlaylistCoverImage() {
		given().
		when().
				get("/playlists/abc12345/cover").
		then().
				statusCode(OK.value());
	}

	@Test
	void shouldReturnRandomTopArtists() {
		given().
				queryParam("count", 5).
		when().
				get("/artists/top").
		then().
				statusCode(OK.value());
	}

	@Test
	void givenAnArtistName_shouldReturnArtists() {
		given().
				queryParam("name", "Manel").
		when().
				get("/artists").
		then().
				statusCode(OK.value());

	}

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
			chromeWebDriver.start();
			of("selenium.remoteWebDriverUrl=http://localhost:%s/webdriver".formatted(chromeWebDriver.getFirstMappedPort()))
			.applyTo(configurableApplicationContext);
		}
	}
}
