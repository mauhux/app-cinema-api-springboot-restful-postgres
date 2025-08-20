package dev.mauhux.apps.cinema.business.domain.services.impl;

import dev.mauhux.apps.cinema.business.api.dtos.CinemaRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaResponseDto;
import dev.mauhux.apps.cinema.business.data.repositories.CinemaRepository;
import dev.mauhux.apps.cinema.business.data.repositories.DistrictRepository;
import dev.mauhux.apps.cinema.business.domain.mappers.CinemaMapper;
import dev.mauhux.apps.cinema.business.domain.services.CinemaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;
    private final DistrictRepository districtRepository;
    private final CinemaMapper cinemaMapper;

    @Override
    public CinemaResponseDto create(CinemaRequestDto dto) {
        var cinema = cinemaMapper.toEntity(dto);
        var district = districtRepository.findById(dto.districtId())
                .orElseThrow(() -> new RuntimeException("District not found"));
        cinema.setDistrict(district);

        var saved = cinemaRepository.save(cinema);
        return cinemaMapper.toDto(saved);
    }

    @Override
    public Page<CinemaResponseDto> getCinemas(Pageable pageable) {
        return cinemaRepository.findAll(pageable)
                .map(cinemaMapper::toDto);
    }

    @Override
    public CinemaResponseDto getCinemaById(UUID id) {
        return cinemaRepository.findById(id)
                .map(cinemaMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Cinema not found"));
    }

    @Override
    public CinemaResponseDto update(UUID id, CinemaRequestDto dto) {
        return cinemaRepository.findById(id)
                .map(existing -> {
                    cinemaMapper.updateEntityFromDto(dto, existing);
                    var district = districtRepository.findById(dto.districtId())
                            .orElseThrow(() -> new RuntimeException("District not found"));
                    existing.setDistrict(district);

                    var saved = cinemaRepository.save(existing);
                    return cinemaMapper.toDto(saved);
                })
                .orElseThrow(() -> new RuntimeException("Cinema not found"));
    }

    @Override
    public void delete(UUID id) {
        if (!cinemaRepository.existsById(id)) {
            throw new RuntimeException("Cinema not found");
        }
        cinemaRepository.deleteById(id);
    }
}
