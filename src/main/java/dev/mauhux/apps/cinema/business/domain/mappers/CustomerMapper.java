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
    @Mapping(source = "loginEmail", target = "email")
    @Mapping(source = "cinema", target = "favoriteCinema")
    CustomerResponseDto toDto(CustomerEntity customerEntity);

    //@Mapping(target = "id", ignore = true)
    @Mapping(source = "email", target = "loginEmail")
    @Mapping(source = "password", target = "loginPassword")
    CustomerEntity toEntity(CustomerRequestDto customerRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loginEmail", source = "email")
    @Mapping(target = "loginPassword", source = "password")
    void updateEntityFromDto(CustomerRequestDto customerRequestDto,
                             @MappingTarget CustomerEntity customerEntity);

}
