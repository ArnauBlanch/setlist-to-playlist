package xyz.arnau.setlisttoplaylist.infrastructure.repository.base;

import lombok.RequiredArgsConstructor;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.ports.ArtistRepository;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtist;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.pivovarit.collectors.ParallelCollectors.parallel;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class ArtistRepositoryBase<TPlatformRepository extends MusicPlatformRepository> implements ArtistRepository {
    private final ExecutorService executorService = Executors.newFixedThreadPool(30);
    private final SetlistFmApiService setlistFmApiService;
    private final TPlatformRepository musicPlatformRepository;

    public List<Artist> getTopArtists() {
        return musicPlatformRepository.getTopArtists();
    }

    public List<Artist> searchByName(String nameQuery) {
        try {
            return setlistFmApiService.searchArtists(nameQuery).stream()
                    .map(SetlistFmArtist::getSortName)
                    .collect(
                            parallel(musicPlatformRepository::getArtist, toList(), executorService, 10))
                    .get().stream()
                    .filter(Optional::isPresent).map(Optional::get).collect(toList());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
