package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import com.google.gson.annotations.SerializedName;
import lombok.Value;

import java.util.List;

@Value
public class SetlistFmArtistSearchResult {
    @SerializedName("artist")
    List<SetlistFmArtist> artists;
}
