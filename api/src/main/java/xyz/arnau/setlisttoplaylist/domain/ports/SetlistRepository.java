package xyz.arnau.setlisttoplaylist.domain.ports;

import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;

import java.util.Optional;

public interface SetlistRepository {
    Optional<Setlist> getSetlist(String setlistId);
}
