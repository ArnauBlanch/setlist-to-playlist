package xyz.arnau.setlisttoplaylist.infrastructure.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.arnau.setlisttoplaylist.domain.Setlist;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.response.SetlistResponse;

@Mapper
public interface SetlistMapper {

    SetlistMapper MAPPER = Mappers.getMapper(SetlistMapper.class);

    SetlistResponse toResponse(Setlist setlist);
}
