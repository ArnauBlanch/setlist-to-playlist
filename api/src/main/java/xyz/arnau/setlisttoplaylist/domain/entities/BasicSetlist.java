package xyz.arnau.setlisttoplaylist.domain.entities;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BasicSetlist(
        String id,
        LocalDate date,
        Venue venue,
        int numSongs
) {}
