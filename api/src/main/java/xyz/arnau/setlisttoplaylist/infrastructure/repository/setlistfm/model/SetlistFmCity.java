package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Value;

@Value
public class SetlistFmCity {
    String name;
    String stateCode;
    String state;
    SetlistFmCountry country;
}
