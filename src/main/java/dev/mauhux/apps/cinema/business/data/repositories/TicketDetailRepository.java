package dev.mauhux.apps.cinema.business.data.repositories;

import dev.mauhux.apps.cinema.business.data.model.entities.TicketDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TicketDetailRepository extends JpaRepository<TicketDetailEntity, UUID> {


    @Query("""
        SELECT CASE WHEN COUNT(td) > 0 THEN true ELSE false END
        FROM TicketDetailEntity td
        WHERE td.seat.id IN :seatIds
        AND td.ticket.showtime.id = :showtimeId
    """)
    boolean existsBySeatIdInAndShowtimeId(@Param("seatIds") List<UUID> seatIds,
                                          @Param("showtimeId") UUID showtimeId);
}
