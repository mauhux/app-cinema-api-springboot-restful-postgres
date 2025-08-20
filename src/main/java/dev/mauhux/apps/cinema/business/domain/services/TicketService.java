package dev.mauhux.apps.cinema.business.domain.services;

import dev.mauhux.apps.cinema.business.api.dtos.TicketPurchaseRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.TicketRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.TicketResponseDto;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    TicketResponseDto createTicket(TicketRequestDto request);
    List<TicketResponseDto> getAllTickets();
    TicketResponseDto getTicketById(UUID ticketId);

    public TicketResponseDto purchaseTicket(TicketPurchaseRequestDto request);
    void deleteTicket(UUID ticketId);
}
