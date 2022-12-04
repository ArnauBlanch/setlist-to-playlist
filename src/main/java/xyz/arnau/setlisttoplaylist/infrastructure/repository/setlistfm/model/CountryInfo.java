package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CountryInfo {
    private final String code;
    private final String name;
}
