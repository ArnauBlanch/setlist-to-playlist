package xyz.arnau.setlisttoplaylist.infrastructure.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.arnau.setlisttoplaylist.domain.entities.Artist;
import xyz.arnau.setlisttoplaylist.infrastructure.controller.response.ArtistResponse;

@Mapper
public interface ArtistMapper {

    ArtistMapper MAPPER = Mappers.getMapper(ArtistMapper.class);

    ArtistResponse toResponse(Artist artist);
}
