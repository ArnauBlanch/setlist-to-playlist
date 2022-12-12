package xyz.arnau.setlisttoplaylist.infrastructure.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.arnau.setlisttoplaylist.domain.Playlist;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.response.PlaylistResponse;

@Mapper
public interface PlaylistMapper {

    PlaylistMapper MAPPER = Mappers.getMapper(PlaylistMapper.class);

    PlaylistResponse toResponse(Playlist playlist);
}
