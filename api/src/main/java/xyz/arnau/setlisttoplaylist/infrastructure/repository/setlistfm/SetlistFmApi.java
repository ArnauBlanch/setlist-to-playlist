package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtistSearchResult;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtistSetlistsPage;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSetlist;

public interface SetlistFmApi {

    @GET("/rest/1.0/setlist/{setlistId}")
    Call<SetlistFmSetlist> getSetlist(@Path("setlistId") String setlistId);

    @GET("/rest/1.0/search/artists")
    Call<SetlistFmArtistSearchResult> searchArtists(@Query("artistName") String name, @Query("sort") String sort);

    @GET("/rest/1.0/artist/{artistId}")
    Call<SetlistFmArtist> getArtist(@Path("artistId") String artistId);

    @GET("/rest/1.0/artist/{artistId}/setlists")
    Call<SetlistFmArtistSetlistsPage> getArtistSetlists(@Path("artistId") String artistId, @Query("p") int page);
}
