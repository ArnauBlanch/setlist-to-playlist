package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SearchResult;

public interface SpotifyApi {

    @GET("/v1/search")
    Call<SearchResult> search(@Query("q") String q, @Query("type") String type, @Query("limit") int limit);
}
