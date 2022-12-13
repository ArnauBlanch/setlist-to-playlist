package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Value;

import java.util.List;

@Value
public class SetlistFmSet {
    List<SetlistFmSong> song;
}
