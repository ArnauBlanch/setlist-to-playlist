package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import xyz.arnau.setlisttoplaylist.domain.entities.*;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSetlist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmVenue;

import java.util.List;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class SetlistFmMapper {

    public static Setlist mapSetlist(SetlistFmSetlist setlist, Artist artist, List<Song> songs) {
        return Setlist.builder()
                .id(setlist.getId())
                .date(parse(setlist.getEventDate(), ofPattern("dd-MM-yyyy")))
                .artist(artist)
                .venue(mapVenue(setlist.getVenue()))
                .songs(songs)
                .build();
    }

    public static BasicSetlist mapSetlist(SetlistFmSetlist setlist) {
        return BasicSetlist.builder()
                .id(setlist.getId())
                .date(parse(setlist.getEventDate(), ofPattern("dd-MM-yyyy")))
                .venue(mapVenue(setlist.getVenue()))
                .numSongs(setlist.getSets().getSet().stream().map(s -> s.getSong().size()).reduce(0, Integer::sum))
                .build();
    }

    public static Venue mapVenue(SetlistFmVenue venueResponse) {
        return Venue.builder()
                .name(venueResponse.getName())
                .city(venueResponse.getCity().getName())
                .country(venueResponse.getCity().getCountry().getName())
                .countryCode(venueResponse.getCity().getCountry().getCode())
                .build();
    }
}
