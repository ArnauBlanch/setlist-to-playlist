package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SetlistFmSetlist {
    String id;
    String eventDate;
    SetlistFmArtist artist;
    SetlistFmVenue venue;
    SetlistFmSets sets;
}
