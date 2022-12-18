package xyz.arnau.setlisttoplaylist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.ports.ArtistRepository;
import xyz.arnau.setlisttoplaylist.domain.ports.SetlistRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final SetlistRepository setlistRepository;
    private final ArtistRepository artistRepository;

    public List<Artist> getTopArtists() {
        return artistRepository.getTopArtists();
    }

    public List<Artist> getAllByName(String name) {
        return setlistRepository.getArtistsByName(name);
    }
}
