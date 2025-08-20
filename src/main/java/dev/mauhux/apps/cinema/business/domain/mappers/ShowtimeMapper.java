package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.ShowtimeRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.ShowtimeResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.ShowtimeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShowtimeMapper {

    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "cinema.id", target = "cinemaId")
    ShowtimeResponseDto toDto(ShowtimeEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "movieId", target = "movie.id")
    @Mapping(source = "cinemaId", target = "cinema.id")
    ShowtimeEntity toEntity(ShowtimeRequestDto dto);

    List<ShowtimeResponseDto> toDtoList(List<ShowtimeEntity> entities);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ShowtimeRequestDto dto, @MappingTarget ShowtimeEntity entity);
}
