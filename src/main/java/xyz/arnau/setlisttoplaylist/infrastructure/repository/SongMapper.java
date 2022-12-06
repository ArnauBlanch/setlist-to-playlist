package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.domain.Song;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.ArtistInfo;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SongInfo;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.Image;

@Service
@RequiredArgsConstructor
public class SongMapper {
    private final SpotifyApiService spotifyApiService;

    public Song map(SongInfo songInfo, ArtistInfo artistInfo) {
        var artist = songInfo.getCover() != null ? songInfo.getCover().getName() : artistInfo.getName();
        return spotifyApiService.searchTrack(artist, songInfo.getName())
                .map(track -> Song.builder()
                        .id(track.getId())
                        .name(track.getName())
                        .durationSeconds(track.getDurationMs() / 1000)
                        .previewUrl(track.getPreviewUrl())
                        .album(track.getAlbum().getName())
                        .albumCoverUrl(track.getAlbum().getImages().stream().findFirst().map(Image::getUrl).orElse(null))
                        .originalArtist(getOriginalArtist(songInfo))
                        .build())
                .orElse(Song.builder()
                        .name(songInfo.getName())
                        .originalArtist(getOriginalArtist(songInfo))
                        .build());
    }

    @Nullable
    private static String getOriginalArtist(SongInfo songInfo) {
        return songInfo.getCover() != null ? songInfo.getCover().getName() : null;
    }

}
