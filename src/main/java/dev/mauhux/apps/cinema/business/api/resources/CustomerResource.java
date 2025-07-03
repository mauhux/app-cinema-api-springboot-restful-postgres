package dev.mauhux.apps.cinema.business.api.resources;

import dev.mauhux.apps.cinema.business.api.dtos.CustomerCommandDto;
import dev.mauhux.apps.cinema.business.api.dtos.CustomerDto;
import dev.mauhux.apps.cinema.business.domain.services.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerResource {

    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        try {
            List<CustomerDto> customers = customerService.findAllCustomers();
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            log.error("Error getting customers.", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable UUID id) {
        try {
            Optional<CustomerDto> customer = customerService.findCustomerById(id);
            return customer
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.noContent().build());
        } catch (Exception e) {
            log.error("Error getting customer by id {}.", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody CustomerCommandDto customerCommandDto) {
        try {
            CustomerDto customerDto = customerService.createCustomer(customerCommandDto);
            URI location = URI.create(String.format("/customers/%s", customerDto.id()));
            return ResponseEntity
                    .created(location)
                    .body(customerDto);
        } catch (Exception e) {
            log.error("Error creating customer.", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@Valid @RequestBody CustomerCommandDto customerCommandDto,
                                              @PathVariable UUID id) {
        try {
            CustomerDto customerDto = customerService.updateCustomer(customerCommandDto, id);
            return ResponseEntity.ok(customerDto);
        } catch (Exception e) {
            log.error("Error updating customer.", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting customer.", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
