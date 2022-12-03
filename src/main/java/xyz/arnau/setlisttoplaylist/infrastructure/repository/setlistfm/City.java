package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class City {
    private String name;
    private String stateCode;
    private String state;
    private Country country;
}
