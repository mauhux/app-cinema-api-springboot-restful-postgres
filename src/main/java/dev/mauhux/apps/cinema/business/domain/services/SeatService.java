package dev.mauhux.apps.cinema.business.domain.services;

import dev.mauhux.apps.cinema.business.api.dtos.SeatRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.SeatResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SeatService {
    SeatResponseDto create(SeatRequestDto requestDto);
    Page<SeatResponseDto> findAll(Pageable pageable);
    SeatResponseDto findById(UUID id);
    SeatResponseDto update(UUID id, SeatRequestDto requestDto);
    void delete(UUID id);
}
