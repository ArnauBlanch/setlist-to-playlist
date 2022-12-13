package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Value;

@Value
public class SetlistFmSong {
    String name;
    SetlistFmArtist cover;
    boolean tape;
}
