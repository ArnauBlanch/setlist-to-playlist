package xyz.arnau.setlisttoplaylist;

import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class SetlistInfoToPlaylistApplicationTests {

	@LocalServerPort
	private int port;

	@Rule
	public WireMockRule mockSpotifyRule = new WireMockRule(wireMockConfig()
			.port(8077)
			.extensions(new ResponseTemplateTransformer(true)));

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	void givenASetlistId_shouldReturnSetlist() {
		given()
				.queryParam("name", "Arctic Monkeys")
		.when()
				.get("/setlist/abc12345")
		.then()
				.statusCode(200)
				.body("artist.name", equalTo("The Strokes"));

	}

}
