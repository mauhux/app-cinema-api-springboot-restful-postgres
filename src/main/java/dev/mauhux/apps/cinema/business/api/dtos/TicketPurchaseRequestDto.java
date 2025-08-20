package dev.mauhux.apps.cinema.business.api.dtos;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record TicketPurchaseRequestDto(
        UUID customerId,
        UUID showtimeId,
        List<UUID> seatIds
) {
}
