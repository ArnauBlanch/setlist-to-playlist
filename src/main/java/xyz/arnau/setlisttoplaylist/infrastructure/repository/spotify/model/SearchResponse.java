package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class SearchResponse {
    private final TracksSearchResult tracks;
}
