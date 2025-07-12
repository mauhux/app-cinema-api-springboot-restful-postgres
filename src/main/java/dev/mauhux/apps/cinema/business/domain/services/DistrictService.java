package dev.mauhux.apps.cinema.business.domain.services;

import dev.mauhux.apps.cinema.business.api.dtos.DistrictResponseDto;

import java.util.List;
import java.util.Optional;

public interface DistrictService {

    List<DistrictResponseDto> getDistricts();

    Optional<DistrictResponseDto> getDistrictById(String id);
}
