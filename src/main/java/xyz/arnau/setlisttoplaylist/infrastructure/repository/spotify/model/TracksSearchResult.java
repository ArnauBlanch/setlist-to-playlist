package xyz.arnau.setlisttoplaylist.infrastructure.repository.spotify.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public final class TracksSearchResult {
    private final List<TrackItem> items;
}
