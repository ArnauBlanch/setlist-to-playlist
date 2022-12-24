package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class SetlistFmArtistSetlistsPage {
    @SerializedName("setlist")
    List<SetlistFmSetlist> setlists;
    int itemsPerPage;
    int page;
    int total;
}
