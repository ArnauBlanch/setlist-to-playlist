package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.base.SetlistRepositoryBase;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmApiService;

@Component
public class SpotifySetlistRepository extends SetlistRepositoryBase<SpotifyRepository> {
    public SpotifySetlistRepository(SpotifyRepository musicPlatformRepository, SetlistFmApiService setlistFmApiService) {
        super(musicPlatformRepository, setlistFmApiService);
    }
}
