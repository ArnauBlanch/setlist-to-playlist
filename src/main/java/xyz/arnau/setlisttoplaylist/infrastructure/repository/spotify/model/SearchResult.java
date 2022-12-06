package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public final class SearchResult<T> {
    private final List<T> items;
}
