package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.CustomerRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.CustomerResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses = {DistrictMapper.class, CinemaMapper.class})
public interface CustomerMapper {

    //@Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "cinema", target = "favoriteCinema")
    CustomerResponseDto toDto(CustomerEntity customerEntity);

    //@Mapping(target = "id", ignore = true)
    CustomerEntity toEntity(CustomerRequestDto customerRequestDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CustomerRequestDto customerRequestDto,
                             @MappingTarget CustomerEntity customerEntity);

}
