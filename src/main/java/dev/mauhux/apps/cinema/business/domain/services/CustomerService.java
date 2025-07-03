package dev.mauhux.apps.cinema.business.domain.services;

import dev.mauhux.apps.cinema.business.api.dtos.CustomerCommandDto;
import dev.mauhux.apps.cinema.business.api.dtos.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDto> findAllCustomers();

    Optional<CustomerDto> findCustomerById(UUID id);

    CustomerDto createCustomer(CustomerCommandDto customerCommandDto);

    CustomerDto updateCustomer(CustomerCommandDto customerCommandDto, UUID id);

    void deleteCustomer(UUID id);

}
