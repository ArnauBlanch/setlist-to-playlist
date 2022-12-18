package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtistSearchResult;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSetlist;

public interface SetlistFmApi {

    @GET("/rest/1.0/setlist/{setlistId}")
    Call<SetlistFmSetlist> getSetlist(@Path("setlistId") String setlistId);

    @GET("/rest/1.0/search/artists")
    Call<SetlistFmArtistSearchResult> searchArtists(@Query("artistName") String name, @Query("sort") String sort);
}
