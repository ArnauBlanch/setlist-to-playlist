package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Venue {
    private String name;
    private City city;
}
