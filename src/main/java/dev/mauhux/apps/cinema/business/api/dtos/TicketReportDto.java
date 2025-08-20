package dev.mauhux.apps.cinema.business.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketReportDto(
        String movieTitle,
        String customerName,
        BigDecimal totalPaid,
        LocalDateTime purchaseDate
) {
}
