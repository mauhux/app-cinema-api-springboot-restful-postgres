package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.CinemaRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.CinemaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = DistrictMapper.class)
public interface CinemaMapper {

    CinemaResponseDto toDto(CinemaEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "district", ignore = true)
    CinemaEntity toEntity(CinemaRequestDto dto);

    List<CinemaResponseDto> toDtoList(List<CinemaEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "district", ignore = true)
    void updateEntityFromDto(CinemaRequestDto cinemaRequestDto, @MappingTarget CinemaEntity cinemaEntity);


}
