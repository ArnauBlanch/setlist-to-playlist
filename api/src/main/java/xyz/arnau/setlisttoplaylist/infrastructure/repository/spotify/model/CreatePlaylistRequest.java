package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import com.google.gson.annotations.SerializedName;
import lombok.Value;

@Value
public class CreatePlaylistRequest {
    String name;
    String description;
    @SerializedName("public")
    boolean isPublic;
}
