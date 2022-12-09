package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.CreatePlaylistRequest;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.MeResponse;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.PlaylistCreatedResponse;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SearchResponse;

public interface SpotifyApi {

    @GET("/v1/search")
    Call<SearchResponse> search(@Query("q") String q,
                                @Query("type") String type,
                                @Query("limit") int limit,
                                @Query("market") String market);

    @GET("/v1/me")
    Call<MeResponse> getUserId(@Header("Authorization") String authorizationHeader);

    @POST("/v1/users/{userId}/playlists")
    Call<PlaylistCreatedResponse> createPlaylist(@Path("userId") String userId,
                                                 @Body CreatePlaylistRequest body,
                                                 @Header("Authorization") String authorizationHeader);

    @PUT("/v1/playlists/{playlistId}/tracks")
    Call<Void> addSongsToPlaylist(@Path("playlistId") String playlistId,
                                  @Query("uris") String uris,
                                  @Header("Authorization") String authorizationHeader);

    @PUT("/v1/playlists/{playlistId}/images")
    @Headers({"Content-Type: application/png"})
    Call<Void> addCoverImageToPlaylist(@Path("playlistId") String playlistId,
                                       @Body RequestBody image,
                                       @Header("Authorization") String authorizationHeader);
}
