package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.CinemaAdminResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaPublicResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaRequestDto;
import dev.mauhux.apps.cinema.business.data.model.entities.CinemaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = DistrictMapper.class)
public interface CinemaMapper {

    CinemaAdminResponseDto toAdminDto(CinemaEntity cinemaEntity);

    CinemaPublicResponseDto toPublicDto(CinemaEntity cinemaEntity);

    CinemaEntity toEntity(CinemaRequestDto cinemaRequestDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CinemaRequestDto cinemaRequestDto, @MappingTarget CinemaEntity cinemaEntity);
}
