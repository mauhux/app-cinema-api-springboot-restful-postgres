package dev.mauhux.apps.cinema.business.api.resources;

import dev.mauhux.apps.cinema.business.api.dtos.TicketPurchaseRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.TicketRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.TicketResponseDto;
import dev.mauhux.apps.cinema.business.domain.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
@Tag(name = "Tickets", description = "Operations related to movie ticket management")
public class TicketResource {
    private final TicketService ticketService;

    @PostMapping
    @Operation(summary = "Create a new ticket", description = "Creates a new ticket with basic details.")
    public ResponseEntity<TicketResponseDto> createTicket(
            @Valid @RequestBody TicketRequestDto request) {
        var response = ticketService.createTicket(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/purchase")
    @Operation(summary = "Purchase ticket", description = "Performs the ticket purchase logic with seat selection.")
    public ResponseEntity<TicketResponseDto> purchaseTicket(
            @Valid @RequestBody TicketPurchaseRequestDto request) {
        var response = ticketService.purchaseTicket(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all tickets", description = "Returns a list of all tickets.")
    public ResponseEntity<List<TicketResponseDto>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by ID", description = "Fetches a ticket using its ID.")
    public ResponseEntity<TicketResponseDto> getTicketById(
            @Parameter(description = "Ticket ID") @PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a ticket", description = "Deletes a ticket using its ID.")
    public ResponseEntity<Void> deleteTicket(
            @Parameter(description = "Ticket ID") @PathVariable UUID id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
