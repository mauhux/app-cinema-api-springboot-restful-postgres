package dev.mauhux.apps.cinema.business.domain.services;

import dev.mauhux.apps.cinema.business.api.dtos.ShowtimeRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.ShowtimeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.UUID;

public interface ShowtimeService {

    ShowtimeResponseDto create(ShowtimeRequestDto requestDto);

    Page<ShowtimeResponseDto> findAll(Pageable pageable);

    ShowtimeResponseDto findById(UUID id);

    ShowtimeResponseDto update(UUID id, ShowtimeRequestDto requestDto);

    void delete(UUID id);
}
