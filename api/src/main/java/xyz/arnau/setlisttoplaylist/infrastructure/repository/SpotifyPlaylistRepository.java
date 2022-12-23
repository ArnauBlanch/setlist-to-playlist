package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.arnau.setlisttoplaylist.domain.dto.CreatePlaylistCommand;
import xyz.arnau.setlisttoplaylist.domain.entities.Playlist;
import xyz.arnau.setlisttoplaylist.domain.ports.PlaylistRepository;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.playlist.CreatePlaylistRequest;

@Component
@RequiredArgsConstructor
public class SpotifyPlaylistRepository implements PlaylistRepository {

    private final SpotifyApiService spotifyApiService;

    @Override
    public Playlist create(CreatePlaylistCommand createPlaylistCommand, String authorizationHeader) {
        var userId = spotifyApiService.getUserId(authorizationHeader);
        var playlistCreated = spotifyApiService.createPlaylist(userId,
                new CreatePlaylistRequest(
                        createPlaylistCommand.name(),
                        createPlaylistCommand.description(),
                        createPlaylistCommand.isPublic()),
                authorizationHeader);

        spotifyApiService.addSongsToPlaylist(playlistCreated.getId(), createPlaylistCommand.songIds(), authorizationHeader);
        spotifyApiService.addCoverImageToPlaylist(playlistCreated.getId(), createPlaylistCommand.coverImage(), authorizationHeader);

        return new Playlist(playlistCreated.getId());
    }
}
