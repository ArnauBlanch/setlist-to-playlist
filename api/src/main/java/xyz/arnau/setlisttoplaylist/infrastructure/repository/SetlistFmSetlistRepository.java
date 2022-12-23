package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.domain.entities.Song;
import xyz.arnau.setlisttoplaylist.domain.ports.SetlistRepository;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmMapper;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSet;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.pivovarit.collectors.ParallelCollectors.parallel;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@CommonsLog
public class SetlistFmSetlistRepository implements SetlistRepository {
    private final ExecutorService executorService = Executors.newFixedThreadPool(30);
    private final SetlistFmApiService setlistFmApiService;
    private final SpotifyApiService spotifyApiService;
    private final SetlistFmSongMapper songMapper;

    @Cacheable(value = "setlists", key = "#setlistId", unless="#result == null")
    public Optional<Setlist> getSetlist(String setlistId) {
            return setlistFmApiService.getSetlist(setlistId)
                    .flatMap(setlist -> spotifyApiService.searchArtist(setlist.getArtist().getName())
                    .map(artist -> Setlist.builder()
                            .date(parseDate(setlist.getEventDate()))
                            .artist(SpotifyMapper.mapArtist(artist))
                            .venue(SetlistFmMapper.mapVenue(setlist.getVenue()))
                            .songs(mapSongs(setlist.getSets().getSet(), setlist.getArtist()))
                            .build()));
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

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
