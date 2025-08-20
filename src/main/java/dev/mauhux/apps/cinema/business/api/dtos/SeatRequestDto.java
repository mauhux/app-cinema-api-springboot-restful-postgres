package dev.mauhux.apps.cinema.business.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SeatRequestDto(
        @NotNull(message = "Cinema ID is required.")
        UUID cinemaId,

        @NotNull(message = "Room number is required.")
        Integer roomNumber,

        @NotBlank(message = "Seat number is required.")
        String seatNumber
) {
}
