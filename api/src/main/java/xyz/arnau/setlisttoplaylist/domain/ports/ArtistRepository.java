package xyz.arnau.setlisttoplaylist.domain.ports;

import xyz.arnau.setlisttoplaylist.domain.entities.Artist;

import java.util.List;

public interface ArtistRepository {
    List<Artist> getTopArtists();
}
