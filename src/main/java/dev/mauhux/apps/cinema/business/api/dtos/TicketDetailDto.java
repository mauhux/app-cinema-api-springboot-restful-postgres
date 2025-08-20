package dev.mauhux.apps.cinema.business.api.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record TicketDetailDto(
        UUID seatId,
        BigDecimal price
) {
}
