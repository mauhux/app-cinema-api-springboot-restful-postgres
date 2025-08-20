package dev.mauhux.apps.cinema.business.api.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record TicketDetailRequestDto(
        @NotNull(message = "Seat ID is required.")
        UUID seatId,

        @NotNull(message = "Price is required.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
        BigDecimal price
) {
}
