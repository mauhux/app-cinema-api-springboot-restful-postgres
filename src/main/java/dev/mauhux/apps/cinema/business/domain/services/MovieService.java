package dev.mauhux.apps.cinema.business.domain.services;

import dev.mauhux.apps.cinema.business.api.dtos.MovieRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.MovieResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MovieService {

    MovieResponseDto create(MovieRequestDto requestDto);

    Page<MovieResponseDto> findAll(Pageable pageable);

    MovieResponseDto findById(UUID id);

    MovieResponseDto update(UUID id, MovieRequestDto requestDto);

    void delete(UUID id);
}
