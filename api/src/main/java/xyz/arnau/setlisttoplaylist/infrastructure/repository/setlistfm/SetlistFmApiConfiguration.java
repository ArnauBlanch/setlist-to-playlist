package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class SetlistFmApiConfiguration {

    @Value("${setlistFm.api.baseUrl}")
    private String baseUrl;

    @Value("${setlistFm.api.apiKey}")
    private String apiKey;

    @Bean
    public SetlistFmApi setlistFmApi() {
        var httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    var newRequest = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("x-api-key", apiKey)
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();

        var retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SetlistFmApi.class);
    }
}
