package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import com.google.gson.annotations.SerializedName;
import lombok.Value;

@Value
public class SetlistFmArtist {
    @SerializedName("mbid")
    String id;
    String name;
}
