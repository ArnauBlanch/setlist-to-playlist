package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SetlistInfo {
    private String id;
    private String eventDate;
    private ArtistInfo artist;
    private VenueInfo venue;
    private SetsInfo sets;
}
