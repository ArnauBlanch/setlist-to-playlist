package xyz.arnau.setlisttoplaylist.infrastructure.repository.setlistfm;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SetlistInfo {
    private String id;
    private String eventDate;
    private Artist artist;
    private Venue venue;
    private Sets sets;
}
