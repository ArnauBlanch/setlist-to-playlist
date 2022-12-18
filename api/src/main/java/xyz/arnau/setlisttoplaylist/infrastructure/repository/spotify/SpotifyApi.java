package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyArtists;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifySearchResult;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyUserProfile;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist.CreatePlaylistRequest;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist.SpotifyPlaylist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist.SpotifyPlaylistCreated;

public interface SpotifyApi {

    @GET("/v1/search")
    Call<SpotifySearchResult> search(@Query("q") String q,
                                     @Query("type") String type,
                                     @Query("limit") int limit,
                                     @Query("market") String market);

    @GET("/v1/me")
    Call<SpotifyUserProfile> getUserId(@Header("Authorization") String authorizationHeader);

    @POST("/v1/users/{userId}/playlists")
    Call<SpotifyPlaylistCreated> createPlaylist(@Path("userId") String userId,
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

    @GET("/v1/playlists/{playlistId}")
    Call<SpotifyPlaylist> getPlaylist(@Path("playlistId") String playlistId,
                                      @Query("fields") String fields);

    @GET("/v1/artists")
    Call<SpotifyArtists> getSeveralArtists(@Query("ids") String artistIds);
}
