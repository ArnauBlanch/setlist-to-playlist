package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CityInfo {
    private String name;
    private String stateCode;
    private String state;
    private CountryInfo country;
}
