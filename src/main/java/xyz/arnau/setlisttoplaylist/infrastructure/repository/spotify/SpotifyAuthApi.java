package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyAuthToken;

public interface SpotifyAuthApi {
    @POST("/api/token")
    @FormUrlEncoded
    Call<SpotifyAuthToken> getAccessToken(@Header("Authorization") String authorization, @Field("grant_type") String grant_type);
}
