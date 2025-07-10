package dev.mauhux.apps.cinema.business.domain.services;

import dev.mauhux.apps.cinema.business.api.dtos.DepartmentResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.DistrictResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.ProvinceResponseDto;

import java.util.List;

public interface LocationService {

    List<DepartmentResponseDto> getAllDepartments();

    List<ProvinceResponseDto> getProvincesByDepartmentId(String departmentId);

    List<DistrictResponseDto> getDistrictsByProvinceId(String provinceId);
}
