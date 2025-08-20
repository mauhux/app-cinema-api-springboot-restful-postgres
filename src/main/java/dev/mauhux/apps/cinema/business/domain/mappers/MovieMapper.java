package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.MovieRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.MovieResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieResponseDto toDto(MovieEntity entity);

    MovieEntity toEntity(MovieRequestDto dto);

    List<MovieResponseDto> toDtoList(List<MovieEntity> entities);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(MovieRequestDto dto, @MappingTarget MovieEntity entity);
}
