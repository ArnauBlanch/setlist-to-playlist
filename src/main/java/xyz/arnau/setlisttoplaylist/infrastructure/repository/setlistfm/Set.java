package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Set {
    private List<Song> song;
}
