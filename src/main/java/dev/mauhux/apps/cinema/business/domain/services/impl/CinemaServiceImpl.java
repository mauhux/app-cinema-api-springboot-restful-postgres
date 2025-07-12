package dev.mauhux.apps.cinema.business.domain.services.impl;

import dev.mauhux.apps.cinema.business.api.dtos.CinemaAdminResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaPublicResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaRequestDto;
import dev.mauhux.apps.cinema.business.data.repositories.CinemaRepository;
import dev.mauhux.apps.cinema.business.data.repositories.DistrictRepository;
import dev.mauhux.apps.cinema.business.domain.mappers.CinemaMapper;
import dev.mauhux.apps.cinema.business.domain.services.CinemaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;
    private final DistrictRepository districtRepository;
    private final CinemaMapper cinemaMapper;

    @Override
    public List<CinemaPublicResponseDto> getPublicCinemas() {
        return cinemaRepository
                .findAll()
                .stream()
                .map(cinemaMapper::toPublicDto)
                .toList();
    }

    @Override
    public List<CinemaAdminResponseDto> getAdminCinemas() {
        return cinemaRepository
                .findAll()
                .stream()
                .map(cinemaMapper::toAdminDto)
                .toList();
    }

    @Override
    public Optional<CinemaAdminResponseDto> getCinemaById(UUID id) {
        return cinemaRepository.findById(id)
                .map(cinemaMapper::toAdminDto);
    }

    @Override
    public CinemaAdminResponseDto createCinema(CinemaRequestDto cinemaRequestDto) {
        var district = districtRepository.findById(cinemaRequestDto.districtId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        var cinemaEntity = cinemaMapper.toEntity(cinemaRequestDto);
        cinemaEntity.setDistrict(district);

        var saved = cinemaRepository.save(cinemaEntity);

        return cinemaMapper.toAdminDto(saved);
    }

    @Override
    public CinemaAdminResponseDto updateCinema(UUID id, CinemaRequestDto dto) {
        return cinemaRepository
                .findById(id)
                .map(cinemaEntity -> {
                    cinemaMapper.updateEntityFromDto(dto, cinemaEntity);
                    return cinemaMapper.toAdminDto(cinemaRepository.save(cinemaEntity));
                })
                .orElseThrow(() -> {
                    log.error("Cinema with ID {} not found.", id);
                    return new RuntimeException("Cinema not found.");
                });
    }

    @Override
    public void deleteCinema(UUID id) {
        cinemaRepository
                .findById(id)
                .ifPresent(cinemaRepository::delete);
    }
}
