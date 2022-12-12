package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import com.pivovarit.collectors.ParallelCollectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import xyz.arnau.setlisttoplaylist.domain.*;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.SongMapper;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.ArtistInfo;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetInfo;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistInfo;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.VenueInfo;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.ArtistItem;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.Image;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SetlistFmSetlistRepository implements SetlistRepository {

    private final ExecutorService executorService = Executors.newFixedThreadPool(30);

    private final SetlistFmApi setlistFmApi;
    private final SpotifyApiService spotifyApiService;
    private final SongMapper songMapper;

    @Cacheable(value = "setlists", key = "#id", unless="#result == null")
    public Optional<Setlist> getSetlist(String id) {
        try {
            Response<SetlistInfo> setlistInfoResponse = setlistFmApi.getSetlist(id).execute();
            SetlistInfo setlist = setlistInfoResponse.body();

            if (setlist != null) {
                Optional<ArtistItem> artist = spotifyApiService.searchArtist(setlist.getArtist().getName());

                return Optional.of(
                        Setlist.builder()
                                .date(parseDate(setlist.getEventDate()))
                                .artist(artist.map(SetlistFmSetlistRepository::mapArtist).orElse(null))
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
        try {
            return setsInfo.stream()
                    .flatMap(setInfo -> setInfo.getSong().stream())
                    .collect(ParallelCollectors.parallel(s -> songMapper.map(s, artistInfo), Collectors.toList(), executorService, 10))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Artist mapArtist(ArtistItem artistItem) {
        return Artist.builder()
                .name(artistItem.getName())
                .imageUrl(artistItem.getImages().stream().findFirst().map(Image::getUrl).orElse(null))
                .build();
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
