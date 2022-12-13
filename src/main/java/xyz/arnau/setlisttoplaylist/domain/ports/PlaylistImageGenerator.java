package xyz.arnau.setlisttoplaylist.domain.ports;

import xyz.arnau.setlisttoplaylist.domain.entities.Setlist;

public interface PlaylistImageGenerator {
    byte[] generateImage(Setlist setlist);
}
