package dev.mauhux.apps.cinema.business.domain.services;

import dev.mauhux.apps.cinema.business.api.dtos.CinemaRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CinemaService {


    CinemaResponseDto create(CinemaRequestDto dto);

    Page<CinemaResponseDto> getCinemas(Pageable pageable);

    CinemaResponseDto getCinemaById(UUID id);

    CinemaResponseDto update(UUID id, CinemaRequestDto dto);

    void delete(UUID id);
}
