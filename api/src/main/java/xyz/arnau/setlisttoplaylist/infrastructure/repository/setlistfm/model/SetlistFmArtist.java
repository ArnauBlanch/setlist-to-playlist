package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SetlistFmArtist {
    @SerializedName("mbid")
    String id;
    String name;
    String sortName;
    String disambiguation;
}
