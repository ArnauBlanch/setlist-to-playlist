package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.domain.CreatePlaylistCommand;
import xyz.arnau.setlisttoplaylist.domain.Playlist;
import xyz.arnau.setlisttoplaylist.domain.PlaylistRepository;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.CreatePlaylistRequest;

@Component
@RequiredArgsConstructor
public class SpotifyPlaylistRepository implements PlaylistRepository {

    private final SpotifyApiService spotifyApiService;

    @Override
    public Playlist create(CreatePlaylistCommand createPlaylistCommand, String authorizationHeader) {
        var userId = spotifyApiService.getUserId(authorizationHeader);
        var playlist = spotifyApiService.createPlaylist(userId,
                new CreatePlaylistRequest(
                        createPlaylistCommand.name(),
                        createPlaylistCommand.description(),
                        createPlaylistCommand.isPublic()),
                authorizationHeader);
        spotifyApiService.addSongsToPlaylist(playlist.id(), createPlaylistCommand.songIds(), authorizationHeader);
        spotifyApiService.addCoverImageToPlaylist(playlist.id(), createPlaylistCommand.coverImage(), authorizationHeader);
        return playlist;
    }
}
