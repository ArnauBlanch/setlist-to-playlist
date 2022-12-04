package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SongInfo {
    private final String name;
    private final ArtistInfo cover;
    private final boolean tape;
}
