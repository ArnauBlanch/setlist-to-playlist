package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Artist {
    private String mbid;
    private String name;
}
