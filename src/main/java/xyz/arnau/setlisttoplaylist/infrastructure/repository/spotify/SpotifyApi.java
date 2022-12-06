package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import retrofit2.Call;
import retrofit2.http.*;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.CreatePlaylistRequest;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.MeResponse;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.PlaylistCreatedResponse;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SearchResponse;

public interface SpotifyApi {

    @GET("/v1/search")
    Call<SearchResponse> search(@Query("q") String q, @Query("type") String type, @Query("limit") int limit);

    @GET("/v1/me")
    Call<MeResponse> getUserId(@Header("Authorization") String authorizationHeader);

    @POST("/v1/users/{userId}/playlists")
    Call<PlaylistCreatedResponse> createPlaylist(@Path("userId") String userId,
                                                 @Body CreatePlaylistRequest body,
                                                 @Header("Authorization") String authorizationHeader);
}
