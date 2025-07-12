package dev.mauhux.apps.cinema.business.domain.services.impl;

import dev.mauhux.apps.cinema.business.api.dtos.CustomerRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.CustomerResponseDto;
import dev.mauhux.apps.cinema.business.data.model.entities.CustomerEntity;
import dev.mauhux.apps.cinema.business.data.repositories.CinemaRepository;
import dev.mauhux.apps.cinema.business.data.repositories.CustomerRepository;
import dev.mauhux.apps.cinema.business.data.repositories.DistrictRepository;
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
    private final DistrictRepository districtRepository;
    private final CinemaRepository cinemaRepository;
    private final CustomerMapper customerMapper;

    @Override

    public List<CustomerResponseDto> getCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    @Override
    public Optional<CustomerResponseDto> getCustomerById(UUID id) {
        return customerRepository
                .findById(id)
                .map(customerMapper::toDto);
    }

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        var district = districtRepository.findById(customerRequestDto.districtId())
                .orElseThrow(() -> new RuntimeException("District not found"));
        var cinema = cinemaRepository.findById(customerRequestDto.cinemaId())
                .orElseThrow(() -> new RuntimeException("Cinema not found"));

        CustomerEntity entity = customerMapper.toEntity(customerRequestDto);
        entity.setDistrict(district);
        entity.setCinema(cinema);

        CustomerEntity saved = customerRepository.save(entity);
        return customerMapper.toDto(saved);
    }

    @Override
    public CustomerResponseDto updateCustomer(UUID id, CustomerRequestDto customerRequestDto) {
        return customerRepository.findById(id)
                .map(existing -> {
                    var district = districtRepository.findById(customerRequestDto.districtId())
                            .orElseThrow(() -> new RuntimeException("District not found"));
                    var cinema = cinemaRepository.findById(customerRequestDto.cinemaId())
                            .orElseThrow(() -> new RuntimeException("Cinema not found"));

                    customerMapper.updateEntityFromDto(customerRequestDto, existing);
                    existing.setDistrict(district);
                    existing.setCinema(cinema);

                    CustomerEntity saved = customerRepository.save(existing);
                    return customerMapper.toDto(saved);
                })
                .orElseThrow(() -> {
                    log.error("Customer with ID {} not found", id);
                    return new RuntimeException("Customer not found");
                });
    }

    @Override
    public void deleteCustomer(UUID id) {
        customerRepository
                .findById(id)
                .ifPresent(customerRepository::delete);
    }
}
