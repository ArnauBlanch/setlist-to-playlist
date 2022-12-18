package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.domain.entities.Song;
import xyz.arnau.setlisttoplaylist.domain.entities.Venue;
import xyz.arnau.setlisttoplaylist.domain.ports.SetlistRepository;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSet;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSetlist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmVenue;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyImage;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.pivovarit.collectors.ParallelCollectors.parallel;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@CommonsLog
public class SetlistFmSetlistRepository implements SetlistRepository {

    public static final int MAX_ARTIST_SEARCH_RESULTS = 10;
    private final ExecutorService executorService = Executors.newFixedThreadPool(30);

    private final SetlistFmApi setlistFmApi;
    private final SpotifyApiService spotifyApiService;
    private final SetlistFmSongMapper songMapper;

    @Cacheable(value = "setlists", key = "#setlistId", unless="#result == null")
    public Optional<Setlist> getSetlist(String setlistId) {
        try {
            Response<SetlistFmSetlist> response = setlistFmApi.getSetlist(setlistId).execute();
            SetlistFmSetlist setlist = response.body();

            if (setlist != null) {
                Optional<SpotifyArtist> artist = spotifyApiService.searchArtist(setlist.getArtist().getName());

                return Optional.of(
                        Setlist.builder()
                                .date(parseDate(setlist.getEventDate()))
                                .artist(artist.map(SetlistFmSetlistRepository::mapArtist).orElse(null))
                                .venue(mapVenue(setlist.getVenue()))
                                .songs(mapSongs(setlist.getSets().getSet(), setlist.getArtist()))
                                .build());
            }
        } catch (IOException | ParseException e) {
            log.error("Could not get setlist (setlistId=%s)".formatted(setlistId));
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public List<Artist> getArtistsByName(String name) {
        try {
            var response = setlistFmApi.searchArtists(name, "relevance").execute();
            if (response.isSuccessful() && response.body() != null && response.body().getArtists() != null
                    && !response.body().getArtists().isEmpty()) {
                List<Optional<Artist>> artists = response.body().getArtists().stream()
                        .limit(MAX_ARTIST_SEARCH_RESULTS)
                        .map(SetlistFmArtist::getSortName)
                        .collect(parallel(searchName -> spotifyApiService.searchArtist(searchName)
                                        .map(SetlistFmSetlistRepository::mapArtist), toList(), executorService, 10)).get();

                return artists.stream().filter(Optional::isPresent).map(Optional::get).collect(toList());

            } else if (response.isSuccessful() || response.code() == HttpStatus.NOT_FOUND.value()) {
                return emptyList();
            } else {
                log.error("Could not get artists (name=%s)".formatted(name));
                throw new RuntimeException("Setlist.fm API error");
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            log.error("Could not get artists (name=%s)".formatted(name));
            throw new RuntimeException(e);
        }
    }

    private List<Song> mapSongs(List<SetlistFmSet> sets, SetlistFmArtist artist) {
        try {
            return sets.stream()
                    .flatMap(setlistSet -> setlistSet.getSong().stream())
                    .collect(parallel(s -> songMapper.map(s, artist), toList(), executorService, 10))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Artist mapArtist(SpotifyArtist artistResponse) {
        return Artist.builder()
                .name(artistResponse.getName())
                .imageUrl(artistResponse.getImages().stream().findFirst().map(SpotifyImage::getUrl).orElse(null))
                .build();
    }

    private static Venue mapVenue(SetlistFmVenue venueResponse) {
        return Venue.builder()
                .name(venueResponse.getName())
                .city(venueResponse.getCity().getName())
                .country(venueResponse.getCity().getCountry().getName())
                .countryCode(venueResponse.getCity().getCountry().getCode())
                .build();
    }

    private LocalDate parseDate(String date) throws ParseException {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
