package xyz.arnau.setlisttoplaylist.domain;

import java.util.Optional;

public interface SetlistRepository {
    Optional<Setlist> getSetlist(String id);
}
