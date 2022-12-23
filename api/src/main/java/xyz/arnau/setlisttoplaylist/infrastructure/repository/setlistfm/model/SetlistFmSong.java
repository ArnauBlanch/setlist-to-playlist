package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SetlistFmSong {
    String name;
    SetlistFmArtist cover;
    boolean tape;
}
