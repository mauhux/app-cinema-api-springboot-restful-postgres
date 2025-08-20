package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.TicketDetailRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.TicketDetailResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.TicketDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketDetailMapper {
    @Mapping(source = "seat.id", target = "seatId")
    TicketDetailResponseDto toDto(TicketDetailEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "seat", ignore = true)
    TicketDetailEntity toEntity(TicketDetailRequestDto dto);

    List<TicketDetailResponseDto> toDtoList(List<TicketDetailEntity> entities);
}
