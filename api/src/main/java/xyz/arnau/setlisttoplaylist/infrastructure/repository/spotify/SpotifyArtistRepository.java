package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.base.ArtistRepositoryBase;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.SetlistFmApiService;

@Component
public class SpotifyArtistRepository extends ArtistRepositoryBase<SpotifyRepository> {
    public SpotifyArtistRepository(SetlistFmApiService setlistFmApiService, SpotifyRepository musicPlatformRepository) {
        super(setlistFmApiService, musicPlatformRepository);
    }
}
