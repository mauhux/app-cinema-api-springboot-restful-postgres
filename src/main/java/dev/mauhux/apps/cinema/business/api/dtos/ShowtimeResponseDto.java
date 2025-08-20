package dev.mauhux.apps.cinema.business.api.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record ShowtimeResponseDto(
        UUID id,
        UUID movieId,
        UUID cinemaId,
        Integer roomNumber,
        LocalDate showDate,
        LocalTime startTime,
        BigDecimal price
) {
}
