package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VenueInfo {
    private String name;
    private CityInfo city;
}
