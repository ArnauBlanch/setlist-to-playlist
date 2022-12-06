package xyz.arnau.setlisttoplaylist.domain;

public interface PlaylistRepository {
    Playlist create(CreatePlaylistCommand createPlaylistCommand, String userToken);
}
