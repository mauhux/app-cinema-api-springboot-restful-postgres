package dev.mauhux.apps.cinema.business.domain.mappers;

import dev.mauhux.apps.cinema.business.api.dtos.CustomerCommandDto;
import dev.mauhux.apps.cinema.business.api.dtos.CustomerDto;
import dev.mauhux.apps.cinema.business.data.model.entities.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "firstName", target = "firstName")
    CustomerDto toDto(CustomerEntity customerEntity);

    @Mapping(target = "id", ignore = true)
    CustomerEntity toEntity(CustomerCommandDto customerCommandDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CustomerCommandDto customerCommandDto,
                             @MappingTarget CustomerEntity customerEntity);

}
