package xyz.arnau.setlisttoplaylist.infrastructure.repository.base;

import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.entities.Song;

import java.util.List;
import java.util.Optional;

public interface MusicPlatformRepository {
    Optional<Artist> getArtist(String nameQuery);
    Optional<Song> getSong(String artistName, String songName, boolean isCover);
    List<Artist> getTopArtists();
}
