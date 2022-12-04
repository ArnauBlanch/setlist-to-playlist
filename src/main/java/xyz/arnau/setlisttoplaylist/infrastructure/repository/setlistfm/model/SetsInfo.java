package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SetsInfo {
    private final List<SetInfo> set;
}
