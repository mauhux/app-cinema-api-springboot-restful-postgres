package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.DistrictResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.DistrictEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DistrictMapper {

    @Mapping(source = "districtId", target = "id")
    @Mapping(source = "districtName", target = "name")
    DistrictResponseDto toDto(DistrictEntity entity);
}
