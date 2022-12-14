package xyz.arnau.setlisttoplaylist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.exceptions.ArtistNotFoundException;
import xyz.arnau.setlisttoplaylist.domain.ports.ArtistRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    public List<Artist> getTopArtists() {
        return artistRepository.getTopArtists();
    }

    public List<Artist> searchByName(String nameQuery) {
        return artistRepository.searchByName(nameQuery);
    }

    public Artist getById(String artistId) {
        return artistRepository.getById(artistId)
                .orElseThrow(ArtistNotFoundException::new);
    }
}
