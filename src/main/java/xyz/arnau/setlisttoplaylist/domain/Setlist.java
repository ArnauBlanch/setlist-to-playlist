package xyz.arnau.setlisttoplaylist.domain;

import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record Setlist(Date date, Artist artist, Venue venue, List<Song> songs) {}
