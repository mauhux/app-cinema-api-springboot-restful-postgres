package dev.mauhux.apps.cinema.business.api.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record TickeRequestDto(
        UUID customerId,
        UUID showtimeId,
        List<UUID> seatIds,
        BigDecimal totalPrice
) {
}
