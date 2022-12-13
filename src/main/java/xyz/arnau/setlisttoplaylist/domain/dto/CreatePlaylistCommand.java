package xyz.arnau.setlisttoplaylist.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatePlaylistCommand(String name, String description, boolean isPublic, List<String> songIds,
                                    byte[] coverImage) {
}
