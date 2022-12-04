package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CityInfo {
    private final String name;
    private final String stateCode;
    private final String state;
    private final CountryInfo country;
}
