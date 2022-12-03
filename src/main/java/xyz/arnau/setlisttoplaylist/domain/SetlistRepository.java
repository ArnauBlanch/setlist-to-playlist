package xyz.arnau.setlisttoplaylist.domain;

import java.io.IOException;
import java.util.Optional;

public interface SetlistRepository {
    Optional<Setlist> getSetlist(String id);
}
