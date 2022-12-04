package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SetlistInfo {
    private final String id;
    private final String eventDate;
    private final ArtistInfo artist;
    private final VenueInfo venue;
    private final SetsInfo sets;
}
