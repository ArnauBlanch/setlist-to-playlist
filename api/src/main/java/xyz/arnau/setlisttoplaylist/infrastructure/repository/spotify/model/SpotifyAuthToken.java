package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Value;

@Value
public class SpotifyAuthToken {
    String accessToken;
    String tokenType;
    int expiresIn;
}
