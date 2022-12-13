package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import com.google.gson.GsonBuilder;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyAuthToken;

import java.time.LocalDateTime;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static java.time.temporal.ChronoUnit.SECONDS;

@Configuration
public class SpotifyConfiguration {

    @Value("${spotify.api.baseUrl}")
    private String baseUrl;

    @Value("${spotify.api.accountsBaseUrl}")
    private String accountsBaseUrl;

    @Value("${spotify.api.clientId}")
    private String clientId;

    @Value("${spotify.api.clientSecret}")
    private String clientSecret;

    private SpotifyAuthToken authToken;
    private LocalDateTime tokenExpiration;

    @Bean
    public SpotifyApi spotifyApi(SpotifyAuthApi spotifyAuthApi) {
        var httpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            var credentials = Credentials.basic(clientId, clientSecret);
            if (authToken == null || LocalDateTime.now().isAfter(tokenExpiration)) {
                authToken = spotifyAuthApi.getAccessToken(credentials, "client_credentials").execute().body();
                tokenExpiration = LocalDateTime.now().plus(authToken.getExpiresIn() - 100, SECONDS);
            }

            if (chain.request().header("Authorization") != null)
                return chain.proceed(chain.request());

            var newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", authToken.getTokenType() + " " + authToken.getAccessToken())
                    .build();
            return chain.proceed(newRequest);
        }).build();

        var retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create()))
                .build();

        return retrofit.create(SpotifyApi.class);
    }

    @Bean
    public SpotifyAuthApi spotifyAuthApi() {
        var retrofit = new Retrofit.Builder()
                .baseUrl(accountsBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create()))
                .build();

        return retrofit.create(SpotifyAuthApi.class);
    }
}
