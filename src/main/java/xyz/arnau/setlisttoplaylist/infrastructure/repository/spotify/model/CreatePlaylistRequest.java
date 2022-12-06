package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreatePlaylistRequest {
    private final String name;
    private final String description;
    @SerializedName("public")
    private final boolean isPublic;
}
