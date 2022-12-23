package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify;

import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyArtist;
import xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model.SpotifyImage;

public class SpotifyMapper {
    public static Artist mapArtist(SpotifyArtist artistResponse) {
        return Artist.builder()
                .name(artistResponse.getName())
                .imageUrl(artistResponse.getImages().stream().findFirst().map(SpotifyImage::getUrl).orElse(null))
                .build();
    }
}
