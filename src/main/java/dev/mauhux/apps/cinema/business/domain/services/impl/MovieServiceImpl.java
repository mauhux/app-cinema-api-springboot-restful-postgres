package dev.mauhux.apps.cinema.business.domain.services.impl;

import dev.mauhux.apps.cinema.business.api.dtos.MovieRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.MovieResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.MovieEntity;
import dev.mauhux.apps.cinema.business.data.repositories.MovieRepository;
import dev.mauhux.apps.cinema.business.domain.mappers.MovieMapper;
import dev.mauhux.apps.cinema.business.domain.services.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public MovieResponseDto create(MovieRequestDto movieRequestDto) {
        MovieEntity movie = movieMapper.toEntity(movieRequestDto);
        MovieEntity saved = movieRepository.save(movie);
        return movieMapper.toDto(saved);
    }

    @Override
    public Page<MovieResponseDto> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(movieMapper::toDto);
    }

    @Override
    public MovieResponseDto findById(UUID id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Override
    public MovieResponseDto update(UUID id, MovieRequestDto requestDto) {
        return movieRepository.findById(id)
                .map(existing -> {
                    movieMapper.updateEntityFromDto(requestDto, existing);
                    return movieRepository.save(existing);
                })
                .map(movieMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Override
    public void delete(UUID id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found");
        }
        movieRepository.deleteById(id);
    }
}
