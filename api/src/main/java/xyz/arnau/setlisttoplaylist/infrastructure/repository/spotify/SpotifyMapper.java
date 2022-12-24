package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.domain.entities.Song;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyImage;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyTrack;

public class SpotifyMapper {
    public static Artist mapArtist(String artistId, SpotifyArtist spotifyArtist) {
        return Artist.builder()
                .id(artistId)
                .musicPlatformId(spotifyArtist.getId())
                .name(spotifyArtist.getName())
                .imageUrl(spotifyArtist.getImages().stream().findFirst().map(SpotifyImage::getUrl).orElse(null))
                .build();
    }

    public static Song mapSong(SpotifyTrack spotifyTrack, boolean isCover) {
        return Song.builder()
                .musicPlatformId(spotifyTrack.getId())
                .name(spotifyTrack.getName())
                .durationSeconds(spotifyTrack.getDurationMs() / 1000)
                .previewUrl(spotifyTrack.getPreviewUrl())
                .albumName(spotifyTrack.getAlbum().getName())
                .albumCoverUrl(spotifyTrack.getAlbum().getImages().stream().findFirst().map(SpotifyImage::getUrl).orElse(null))
                .originalArtistName(isCover ? spotifyTrack.getArtists().stream().findFirst().map(SpotifyArtist::getName).orElse(null) : null)
                .build();
    }
}
