package dev.mauhux.apps.cinema.business.domain.services.impl;

import dev.mauhux.apps.cinema.business.api.dtos.ShowtimeRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.ShowtimeResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.ShowtimeEntity;
import dev.mauhux.apps.cinema.business.data.repositories.CinemaRepository;
import dev.mauhux.apps.cinema.business.data.repositories.MovieRepository;
import dev.mauhux.apps.cinema.business.data.repositories.ShowtimeRepository;
import dev.mauhux.apps.cinema.business.domain.mappers.ShowtimeMapper;
import dev.mauhux.apps.cinema.business.domain.services.ShowtimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final ShowtimeMapper showtimeMapper;

    @Override
    public ShowtimeResponseDto create(ShowtimeRequestDto dto) {
        var movie = movieRepository.findById(dto.movieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        var cinema = cinemaRepository.findById(dto.cinemaId())
                .orElseThrow(() -> new RuntimeException("Cinema not found"));

        ShowtimeEntity showtime = showtimeMapper.toEntity(dto);
        showtime.setMovie(movie);
        showtime.setCinema(cinema);

        var saved = showtimeRepository.save(showtime);
        return showtimeMapper.toDto(saved);
    }

    @Override
    public Page<ShowtimeResponseDto> findAll(Pageable pageable) {
        return showtimeRepository.findAll(pageable)
                .map(showtimeMapper::toDto);
    }

    @Override
    public ShowtimeResponseDto findById(UUID id) {
        return showtimeRepository.findById(id)
                .map(showtimeMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
    }

    @Override
    public ShowtimeResponseDto update(UUID id, ShowtimeRequestDto dto) {
        return showtimeRepository.findById(id)
                .map(existing -> {
                    var movie = movieRepository.findById(dto.movieId())
                            .orElseThrow(() -> new RuntimeException("Movie not found"));

                    var cinema = cinemaRepository.findById(dto.cinemaId())
                            .orElseThrow(() -> new RuntimeException("Cinema not found"));

                    showtimeMapper.updateEntityFromDto(dto, existing);
                    existing.setMovie(movie);
                    existing.setCinema(cinema);

                    var updated = showtimeRepository.save(existing);
                    return showtimeMapper.toDto(updated);
                })
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
    }

    @Override
    public void delete(UUID id) {
        if (!showtimeRepository.existsById(id)) {
            throw new RuntimeException("Showtime not found");
        }
        showtimeRepository.deleteById(id);
    }
}
