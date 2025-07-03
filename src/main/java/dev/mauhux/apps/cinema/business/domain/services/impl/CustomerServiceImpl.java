package dev.mauhux.apps.cinema.business.domain.services.impl;

import dev.mauhux.apps.cinema.business.api.dtos.CustomerCommandDto;
import dev.mauhux.apps.cinema.business.api.dtos.CustomerDto;
import dev.mauhux.apps.cinema.business.data.repositories.CustomerRepository;
import dev.mauhux.apps.cinema.business.domain.mappers.CustomerMapper;
import dev.mauhux.apps.cinema.business.domain.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override

    public List<CustomerDto> findAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    @Override
    public Optional<CustomerDto> findCustomerById(UUID id) {
        return customerRepository
                .findById(id)
                .map(customerMapper::toDto);
    }

    @Override
    public CustomerDto createCustomer(CustomerCommandDto customerCommandDto) {
        return customerMapper
                .toDto(
                        customerRepository.save(
                                customerMapper.toEntity(customerCommandDto)
                        )
                );
    }

    @Override
    public CustomerDto updateCustomer(CustomerCommandDto customerCommandDto, UUID id) {
        return customerRepository
                .findById(id)
                .map(customerEntity -> {
                    customerMapper.updateEntityFromDto(customerCommandDto, customerEntity);
                    return customerMapper.toDto(customerRepository.save(customerEntity));
                })
                .orElseThrow(() -> {
                    log.error("Customer with id {} not found.", id);
                    return new RuntimeException("Customer with id " + id + " not found.");
                });
    }

    @Override
    public void deleteCustomer(UUID id) {
        customerRepository
                .findById(id)
                .ifPresent(customerRepository::delete);
    }
}
