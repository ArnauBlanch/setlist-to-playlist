package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import xyz.arnau.setlisttoplaylist.domain.entities.Venue;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmVenue;

public class SetlistFmMapper {

    public static Venue mapVenue(SetlistFmVenue venueResponse) {
        return Venue.builder()
                .name(venueResponse.getName())
                .city(venueResponse.getCity().getName())
                .country(venueResponse.getCity().getCountry().getName())
                .countryCode(venueResponse.getCity().getCountry().getCode())
                .build();
    }
}
