package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.SeatRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.SeatResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.SeatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    @Mapping(source = "cinema.id", target = "cinemaId")
    SeatResponseDto toDto(SeatEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cinemaId", target = "cinema.id")
    SeatEntity toEntity(SeatRequestDto dto);

    List<SeatResponseDto> toDtoList(List<SeatEntity> entities);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(SeatRequestDto dto, @MappingTarget SeatEntity entity);
}
