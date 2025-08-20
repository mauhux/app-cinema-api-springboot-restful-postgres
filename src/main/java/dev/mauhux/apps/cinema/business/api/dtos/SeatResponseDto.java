package dev.mauhux.apps.cinema.business.api.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SeatResponseDto(
        UUID id,
        UUID cinemaId,
        Integer roomNumber,
        String seatNumber
) {
}
