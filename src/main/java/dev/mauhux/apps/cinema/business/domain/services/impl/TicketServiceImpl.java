package dev.mauhux.apps.cinema.business.domain.services.impl;

import dev.mauhux.apps.cinema.business.api.dtos.TicketPurchaseRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.TicketRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.TicketResponseDto;
import dev.mauhux.apps.cinema.business.api.exceptions.NotFoundException;
import dev.mauhux.apps.cinema.business.data.model.entities.*;
import dev.mauhux.apps.cinema.business.data.repositories.*;
import dev.mauhux.apps.cinema.business.domain.mappers.TicketDetailMapper;
import dev.mauhux.apps.cinema.business.domain.mappers.TicketMapper;
import dev.mauhux.apps.cinema.business.domain.services.ShowtimeService;
import dev.mauhux.apps.cinema.business.domain.services.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    public final ShowtimeRepository showtimeRepository;
    public final CustomerRepository customerRepository;
    public final SeatRepository seatRepository;
    public final TicketRepository ticketRepository;
    public final TicketDetailRepository ticketDetailRepository;
    public final TicketMapper ticketMapper;

    @Override
    @Transactional
    public TicketResponseDto purchaseTicket(TicketPurchaseRequestDto request) {
        // 1. Validar existencia del showtime
        ShowtimeEntity showtime = showtimeRepository.findById(request.showtimeId())
                .orElseThrow(() -> new NotFoundException("Showtime not found"));

        // 2. Validar existencia del cliente
        CustomerEntity customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        // 3. Validar asientos y que no estén ocupados
        List<SeatEntity> selectedSeats = seatRepository.findAllById(request.seatIds());
        if (selectedSeats.size() != request.seatIds().size()) {
            throw new RuntimeException("Some seat IDs are invalid");
        }

        boolean anySeatTaken = ticketDetailRepository.existsBySeatIdInAndShowtimeId(
                request.seatIds(), request.showtimeId());
        if (anySeatTaken) throw new RuntimeException("One or more seats already taken");

        // 4. Calcular total
        BigDecimal total = showtime.getPrice().multiply(new BigDecimal(selectedSeats.size()));

        // 5. Crear ticket y detalles
        TicketEntity ticket = TicketEntity.builder()
                .customer(customer)
                .showtime(showtime)
                .totalPrice(total)
                .build();

        List<TicketDetailEntity> details = selectedSeats.stream().map(seat ->
                TicketDetailEntity.builder()
                        .seat(seat)
                        .ticket(ticket)
                        .price(showtime.getPrice())
                        .build()
        ).toList();

        ticket.setDetails(details);
        TicketEntity saved = ticketRepository.save(ticket);

        // 6. Armar respuesta
        return TicketResponseDto.builder()
                .ticketId(saved.getId())
                .customerName(customer.getFirstName() + " " + customer.getLastName())
                .movieTitle(showtime.getMovie().getTitle())
                .cinemaName(showtime.getCinema().getCinemaName())
                .showDate(showtime.getShowDate())
                .showTime(showtime.getStartTime())
                .seatNumbers(details.stream().map(d -> d.getSeat().getSeatNumber()).toList())
                .totalPrice(total)
                .build();
    }

    @Override
    public TicketResponseDto createTicket(TicketRequestDto request) {
        var customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        var showtime = showtimeRepository.findById(request.showtimeId())
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        List<SeatEntity> seats = seatRepository.findAllById(request.seatIds());

        if (seats.size() != request.seatIds().size()) {
            throw new RuntimeException("Some seat IDs are invalid");
        }

        BigDecimal totalPrice = showtime.getPrice().multiply(BigDecimal.valueOf(seats.size()));

        TicketEntity ticket = TicketEntity.builder()
                .customer(customer)
                .showtime(showtime)
                .totalPrice(totalPrice)
                .build();

        List<TicketDetailEntity> details = seats.stream()
                .map(seat -> TicketDetailEntity.builder()
                        .ticket(ticket)
                        .seat(seat)
                        .price(showtime.getPrice())
                        .build())
                .collect(Collectors.toList());

        ticket.setDetails(details);

        TicketEntity saved = ticketRepository.save(ticket);
        return ticketMapper.toDto(saved);
    }

    @Override
    public List<TicketResponseDto> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(ticketMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponseDto getTicketById(UUID ticketId) {
        return ticketRepository.findById(ticketId)
                .map(ticketMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Override
    public void deleteTicket(UUID ticketId) {
        if (!ticketRepository.existsById(ticketId)) {
            throw new RuntimeException("Ticket not found");
        }
        ticketRepository.deleteById(ticketId);
    }


}
