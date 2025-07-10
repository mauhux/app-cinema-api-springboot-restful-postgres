package dev.mauhux.apps.cinema.business.domain.services.impl;

import dev.mauhux.apps.cinema.business.api.dtos.DepartmentResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.DistrictResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.ProvinceResponseDto;
import dev.mauhux.apps.cinema.business.data.repositories.DepartmentRepository;
import dev.mauhux.apps.cinema.business.data.repositories.DistrictRepository;
import dev.mauhux.apps.cinema.business.data.repositories.ProvinceRepository;
import dev.mauhux.apps.cinema.business.domain.mappers.LocationMapper;
import dev.mauhux.apps.cinema.business.domain.services.LocationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final DepartmentRepository departmentRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final LocationMapper locationMapper;

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        log.info("Getting all departments");
        return departmentRepository.findAll()
                .stream()
                .map(locationMapper::toDto)
                .toList();
    }

    @Override
    public List<ProvinceResponseDto> getProvincesByDepartmentId(String departmentId) {
        log.info("Getting provinces for departmentId: {}", departmentId);
        return provinceRepository.findAll()
                .stream()
                .filter(p -> p.getDepartment().getDepartmentId().equals(departmentId))
                .map(locationMapper::toDto)
                .toList();
    }

    @Override
    public List<DistrictResponseDto> getDistrictsByProvinceId(String provinceId) {
        log.info("Getting districts for provinceId: {}", provinceId);
        return districtRepository.findAll()
                .stream()
                .filter(d -> d.getProvince().getProvinceId().equals(provinceId))
                .map(locationMapper::toDto)
                .toList();
    }
}
