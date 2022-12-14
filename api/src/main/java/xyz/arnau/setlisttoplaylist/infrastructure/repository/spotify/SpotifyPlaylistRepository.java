package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.domain.dto.CreatePlaylistCommand;
import xyz.arnau.setlisttoplaylist.domain.entities.Playlist;
import xyz.arnau.setlisttoplaylist.domain.ports.PlaylistRepository;
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
