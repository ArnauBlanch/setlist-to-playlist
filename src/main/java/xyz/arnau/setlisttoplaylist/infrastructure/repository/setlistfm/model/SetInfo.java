package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SetInfo {
    private List<SongInfo> song;
}
