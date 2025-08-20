package dev.mauhux.apps.cinema.business.domain.services.impl;

import dev.mauhux.apps.cinema.business.api.dtos.SeatRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.SeatResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.CinemaEntity;
import dev.mauhux.apps.cinema.business.data.model.entities.SeatEntity;
import dev.mauhux.apps.cinema.business.data.repositories.CinemaRepository;
import dev.mauhux.apps.cinema.business.data.repositories.SeatRepository;
import dev.mauhux.apps.cinema.business.domain.mappers.SeatMapper;
import dev.mauhux.apps.cinema.business.domain.services.SeatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final CinemaRepository cinemaRepository;
    private final SeatMapper seatMapper;

    @Override
    public SeatResponseDto create(SeatRequestDto requestDto) {
        CinemaEntity cinema = cinemaRepository.findById(requestDto.cinemaId())
                .orElseThrow(() -> new RuntimeException("Cinema not found"));

        SeatEntity seat = seatMapper.toEntity(requestDto);
        seat.setCinema(cinema);

        return seatMapper.toDto(seatRepository.save(seat));
    }

    @Override
    public Page<SeatResponseDto> findAll(Pageable pageable) {
        return seatRepository.findAll(pageable).map(seatMapper::toDto);
    }

    @Override
    public SeatResponseDto findById(UUID id) {
        return seatRepository.findById(id)
                .map(seatMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
    }

    @Override
    public SeatResponseDto update(UUID id, SeatRequestDto requestDto) {
        return seatRepository.findById(id)
                .map(existing -> {
                    CinemaEntity cinema = cinemaRepository.findById(requestDto.cinemaId())
                            .orElseThrow(() -> new RuntimeException("Cinema not found"));
                    seatMapper.updateEntityFromDto(requestDto, existing);
                    existing.setCinema(cinema);
                    return seatMapper.toDto(seatRepository.save(existing));
                })
                .orElseThrow(() -> new RuntimeException("Seat not found"));
    }

    @Override
    public void delete(UUID id) {
        if (!seatRepository.existsById(id)) {
            throw new RuntimeException("Seat not found");
        }
        seatRepository.deleteById(id);
    }
}
