package xyz.arnau.setlisttoplaylist.domain.ports;

import xyz.arnau.setlisttoplaylist.domain.entities.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository {
    List<Artist> getTopArtists();
    List<Artist> searchByName(String nameQuery);

    Optional<Artist> getById(String artistId);
}
