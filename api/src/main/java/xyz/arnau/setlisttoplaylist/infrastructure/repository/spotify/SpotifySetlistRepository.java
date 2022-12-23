package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.base.SetlistRepositoryBase;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmApiService;

import java.util.Optional;

import static xyz.arnau.setlisttoplaylist.infrastructure.CacheConfig.SETLISTS;

@Component
public class SpotifySetlistRepository extends SetlistRepositoryBase<SpotifyRepository> {
    public SpotifySetlistRepository(SpotifyRepository musicPlatformRepository, SetlistFmApiService setlistFmApiService) {
        super(musicPlatformRepository, setlistFmApiService);
    }

    @Override
    @Cacheable(value = SETLISTS, key = "{'spotify', #setlistId}", sync = true)
    public Optional<Setlist> getSetlist(String setlistId) {
        return super.getSetlist(setlistId);
    }
}
