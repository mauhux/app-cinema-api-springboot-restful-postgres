package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.TicketRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.TicketResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.TicketDetailEntity;
import dev.mauhux.apps.cinema.business.data.model.entities.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "showtime", ignore = true)
    @Mapping(target = "details", ignore = true)
        // Lo haremos manualmente
    TicketEntity toEntity(TicketRequestDto dto);

    @Mapping(source = "id", target = "ticketId")
    @Mapping(source = "customer.firstName", target = "customerName")
    @Mapping(source = "showtime.movie.title", target = "movieTitle")
    @Mapping(source = "showtime.cinema.name", target = "cinemaName")
    @Mapping(source = "showtime.showDate", target = "showDate")
    @Mapping(source = "showtime.startTime", target = "showTime")
    @Mapping(source = "details", target = "seatNumbers")
    TicketResponseDto toDto(TicketEntity ticket);

    List<TicketResponseDto> toDtoList(List<TicketEntity> entities);

    default List<String> mapSeatNumbers(List<TicketDetailEntity> details) {
        if (details == null) return List.of();
        return details.stream()
                .map(detail -> detail.getSeat().getSeatNumber()) // Asumiendo que tienes `SeatEntity` con `getSeatNumber`
                .toList();
    }

}
