package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.DepartmentResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.DistrictResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.ProvinceResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.DepartmentEntity;
import dev.mauhux.apps.cinema.business.data.model.entities.DistrictEntity;
import dev.mauhux.apps.cinema.business.data.model.entities.ProvinceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "departmentId", target = "id")
    @Mapping(source = "departmentName", target = "name")
    DepartmentResponseDto toDto(DepartmentEntity entity);

    @Mapping(source = "provinceId", target = "id")
    @Mapping(source = "provinceName", target = "name")
    ProvinceResponseDto toDto(ProvinceEntity entity);

    @Mapping(source = "districtId", target = "id")
    @Mapping(source = "districtName", target = "name")
    DistrictResponseDto toDto(DistrictEntity entity);

    List<DepartmentResponseDto> toDepartmentDtoList(List<DepartmentEntity> entities);
    List<ProvinceResponseDto> toProvinceDtoList(List<ProvinceEntity> entities);
    List<DistrictResponseDto> toDistrictDtoList(List<DistrictEntity> entities);
}
