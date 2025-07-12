package dev.mauhux.apps.cinema.business.domain.services.impl;

import dev.mauhux.apps.cinema.business.api.dtos.DistrictResponseDto;
import dev.mauhux.apps.cinema.business.data.repositories.DistrictRepository;
import dev.mauhux.apps.cinema.business.domain.mappers.DistrictMapper;
import dev.mauhux.apps.cinema.business.domain.services.DistrictService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    @Override
    public List<DistrictResponseDto> getDistricts() {
        return districtRepository
                .findAll()
                .stream()
                .map(districtMapper::toDto)
                .toList();
    }

    @Override
    public Optional<DistrictResponseDto> getDistrictById(String id) {
        return districtRepository.findById(id)
                .map(districtMapper::toDto);
    }
}
