package dev.mauhux.apps.cinema.business.data.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "ticket_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ticket_id")
    private TicketEntity ticket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal price;
}
