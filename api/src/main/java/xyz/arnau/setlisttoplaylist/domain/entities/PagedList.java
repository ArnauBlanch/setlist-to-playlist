package xyz.arnau.setlisttoplaylist.domain.entities;

import lombok.Builder;

import java.util.List;

@Builder
public record PagedList<T>(
    List<T> items,
    int page,
    int totalItems,
    int itemsPerPage
) {}
