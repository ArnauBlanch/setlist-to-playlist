package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class Token {
    private final String accessToken;
    private final String tokenType;
    private final int expiresIn;
}
