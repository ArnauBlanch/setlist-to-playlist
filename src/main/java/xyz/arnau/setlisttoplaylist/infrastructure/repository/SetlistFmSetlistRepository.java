package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import xyz.arnau.setlisttoplaylist.domain.*;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmApi;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SetlistFmSetlistRepository implements SetlistRepository {

    private final SetlistFmApi setlistFmApi;
    private final SongMapper songMapper;

    @Override
    public Optional<Setlist> getSetlist(String id) {
        try {
            Response<SetlistInfo> setlistInfoResponse = setlistFmApi.getSetlist(id).execute();
            SetlistInfo setlist = setlistInfoResponse.body();

            if (setlist != null) {
                return Optional.of(
                        Setlist.builder()
                                .date(parseDate(setlist.getEventDate()))
                                .artist(mapArtist(setlist.getArtist()))
                                .venue(mapVenue(setlist.getVenue()))
                                .songs(mapSongs(setlist.getSets().getSet(), setlist.getArtist()))
                                .build());
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    private List<Song> mapSongs(List<SetInfo> setsInfo, ArtistInfo artistInfo) {
        return setsInfo.stream()
                .flatMap(setInfo -> setInfo.getSong().stream())
                .map(song -> songMapper.map(song, artistInfo))
                .collect(Collectors.toList());
    }

    private static Artist mapArtist(ArtistInfo artistInfo) {
        return Artist.builder().name(artistInfo.getName()).build();
    }

    private static Venue mapVenue(VenueInfo venueInfo) {
        return Venue.builder()
                .name(venueInfo.getName())
                .city(venueInfo.getCity().getName())
                .country(venueInfo.getCity().getCountry().getName())
                .countryCode(venueInfo.getCity().getCountry().getCode())
                .build();
    }

    private LocalDate parseDate(String date) throws ParseException {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
