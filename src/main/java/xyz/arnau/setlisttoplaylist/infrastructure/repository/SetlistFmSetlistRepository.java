package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import xyz.arnau.setlisttoplaylist.domain.*;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmApi;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistInfo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SetlistFmSetlistRepository implements SetlistRepository {

    private final SetlistFmApi setlistFmApi;

    @Override
    public Optional<Setlist> getSetlist(String id) {
        try {
            Response<SetlistInfo> setlistInfoResponse = setlistFmApi.getSetlist(id).execute();
            SetlistInfo setlist = setlistInfoResponse.body();

            if (setlist != null) {
                return Optional.of(
                        Setlist.builder()
                                .date(parseDate(setlist.getEventDate()))
                                .artist(Artist.builder().name(setlist.getArtist().getName()).build())
                                .venue(Venue.builder()
                                        .name(setlist.getVenue().getName())
                                        .city(setlist.getVenue().getCity().getName())
                                        .country(setlist.getVenue().getCity().getCountry().getName())
                                        .countryCode(setlist.getVenue().getCity().getCountry().getCode())
                                        .build())
                                .songs(setlist.getSets().getSet().stream()
                                        .flatMap(setInfo -> setInfo.getSong().stream())
                                        .filter(song -> !song.isTape())
                                        .map(song -> Song.builder()
                                                .name(song.getName())
                                                .originalArtist(song.getCover() != null ? song.getCover().getName() : null)
                                                .build())
                                        .collect(Collectors.toList()))
                                .build());
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    private Date parseDate(String date) throws ParseException {
        var format = new SimpleDateFormat("dd-MM-yyyy");
        return format.parse(date);
    }
}
