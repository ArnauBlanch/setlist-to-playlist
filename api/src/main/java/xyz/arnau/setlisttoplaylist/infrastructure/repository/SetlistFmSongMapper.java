package xyz.arnau.setlisttoplaylist.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import xyz.arnau.setlisttoplaylist.domain.entities.Song;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model.SetlistFmSong;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.SpotifyApiService;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyImage;

@Service
@RequiredArgsConstructor
public class SetlistFmSongMapper {
    private final SpotifyApiService spotifyApiService;

    public Song map(SetlistFmSong song, SetlistFmArtist artist) {
        var artistName = song.getCover() != null ? song.getCover().getName() : artist.getName();
        return spotifyApiService.searchTrack(artistName, song.getName())
                .map(track -> Song.builder()
                        .id(track.getId())
                        .name(track.getName())
                        .durationSeconds(track.getDurationMs() / 1000)
                        .previewUrl(track.getPreviewUrl())
                        .albumName(track.getAlbum().getName())
                        .albumCoverUrl(track.getAlbum().getImages().stream().findFirst().map(SpotifyImage::getUrl).orElse(null))
                        .originalArtist(getOriginalArtist(song))
                        .build())
                .orElse(Song.builder()
                        .name(song.getName())
                        .originalArtist(getOriginalArtist(song))
                        .build());
    }

    @Nullable
    private static String getOriginalArtist(SetlistFmSong song) {
        return song.getCover() != null ? song.getCover().getName() : null;
    }

}
