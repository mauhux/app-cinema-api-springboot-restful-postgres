package dev.mauhux.apps.cinema.business.domain.services;

import dev.mauhux.apps.cinema.business.api.dtos.CustomerRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.CustomerResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerResponseDto> getCustomers();

    Optional<CustomerResponseDto> getCustomerById(UUID id);

    CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto);

    CustomerResponseDto updateCustomer(UUID id, CustomerRequestDto customerRequestDto);

    void deleteCustomer(UUID id);

}
