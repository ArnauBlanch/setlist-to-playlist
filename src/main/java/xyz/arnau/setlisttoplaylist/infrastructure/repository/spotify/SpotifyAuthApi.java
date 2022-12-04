package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import retrofit2.Call;
import retrofit2.http.*;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.Token;

public interface SpotifyAuthApi {
    @POST("/api/token")
    @FormUrlEncoded
    Call<Token> getAccessToken(@Header("Authorization") String authorization, @Field("grant_type") String grant_type);
}
