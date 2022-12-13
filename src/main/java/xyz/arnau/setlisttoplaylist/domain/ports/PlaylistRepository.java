package xyz.arnau.setlisttoplaylist.domain.ports;

import xyz.arnau.setlisttoplaylist.domain.dto.CreatePlaylistCommand;
import xyz.arnau.setlisttoplaylist.domain.entities.Playlist;

public interface PlaylistRepository {
    Playlist create(CreatePlaylistCommand createPlaylistCommand, String userToken);
}
