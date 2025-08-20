package dev.mauhux.apps.cinema.business.api.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record ShowtimeRequestDto(
        @NotNull(message = "Movie ID is required.")
        UUID movieId,

        @NotNull(message = "Cinema ID is required.")
        UUID cinemaId,

        @NotNull(message = "Room number is required.")
        Integer roomNumber,

        @NotNull(message = "Show date is required.")
        LocalDate showDate,

        @NotNull(message = "Start time is required.")
        LocalTime startTime,

        @NotNull(message = "Price is required.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
        BigDecimal price
) {
}
