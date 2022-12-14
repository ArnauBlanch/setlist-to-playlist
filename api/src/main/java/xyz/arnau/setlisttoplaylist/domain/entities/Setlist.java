package xyz.arnau.setlisttoplaylist.domain.entities;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record Setlist(
        LocalDate date,
        Artist artist,
        Venue venue,
        List<Song> songs
) {}
