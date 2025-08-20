package dev.mauhux.apps.cinema.business.api.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Builder
public record TicketResponseDto(
        UUID ticketId,
        String customerName,
        String movieTitle,
        String cinemaName,
        LocalDate showDate,
        LocalTime showTime,
        List<String> seatNumbers,
        BigDecimal totalPrice
) {
}
