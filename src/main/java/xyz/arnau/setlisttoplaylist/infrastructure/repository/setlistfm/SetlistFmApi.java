package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistInfo;

public interface SetlistFmApi {

    @GET("/rest/1.0/setlist/{setlistId}")
    Call<SetlistInfo> getSetlist(@Path("setlistId") String setlistId);
}
