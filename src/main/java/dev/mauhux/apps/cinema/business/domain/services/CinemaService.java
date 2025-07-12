package dev.mauhux.apps.cinema.business.domain.services;

import dev.mauhux.apps.cinema.business.api.dtos.CinemaAdminResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaPublicResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaRequestDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CinemaService {

    List<CinemaPublicResponseDto> getPublicCinemas();

    List<CinemaAdminResponseDto> getAdminCinemas();

    Optional<CinemaAdminResponseDto> getCinemaById(UUID id);

    CinemaAdminResponseDto createCinema(CinemaRequestDto dto);

    CinemaAdminResponseDto updateCinema(UUID id, CinemaRequestDto dto);

    void deleteCinema(UUID id);
}
