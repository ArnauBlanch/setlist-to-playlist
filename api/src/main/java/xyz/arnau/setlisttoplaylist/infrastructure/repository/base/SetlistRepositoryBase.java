package xyz.arnau.setlisttoplaylist.infrastructure.repository.base;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.domain.entities.Song;
import xyz.arnau.setlisttoplaylist.domain.ports.SetlistRepository;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSet;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSong;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.pivovarit.collectors.ParallelCollectors.parallel;
import static java.util.stream.Collectors.toList;
import static xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmMapper.mapSetlist;

@RequiredArgsConstructor
public abstract class SetlistRepositoryBase<TPlatformRepository extends MusicPlatformRepository> implements SetlistRepository {
    private final ExecutorService executorService = Executors.newFixedThreadPool(30);
    private final TPlatformRepository musicPlatformRepository;
    private final SetlistFmApiService setlistFmApiService;

    @Cacheable(value = "setlists", key = "#setlistId", unless="#result == null")
    public Optional<Setlist> getSetlist(String setlistId) {
        return setlistFmApiService.getSetlist(setlistId)
                .flatMap(setlist -> musicPlatformRepository.getArtist(setlist.getArtist().getName())
                        .map(artist -> {
                            var songs = mapSongs(setlist.getSets().getSet(), setlist.getArtist());
                            return mapSetlist(setlist, artist, songs);
                        }));
    }

    private List<Song> mapSongs(List<SetlistFmSet> sets, SetlistFmArtist artist) {
        try {
            return sets.stream()
                    .flatMap(setlistSet -> setlistSet.getSong().stream())
                    .collect(parallel(setlistSong -> getSong(setlistSong, artist), toList(), executorService, 10))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Song getSong(SetlistFmSong setlistSong, SetlistFmArtist artist) {
        String artistName = setlistSong.getCover() != null ? setlistSong.getCover().getName() : artist.getName();
        return musicPlatformRepository.getSong(artistName, setlistSong.getName(), setlistSong.getCover() != null)
                .orElse(Song.builder()
                        .name(setlistSong.getName())
                        .originalArtistName(setlistSong.getCover() != null ? setlistSong.getCover().getName() : null)
                        .build());
    }
}
