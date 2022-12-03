package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Song {
    private String name;
    private Artist cover;
    private boolean tape;
}
