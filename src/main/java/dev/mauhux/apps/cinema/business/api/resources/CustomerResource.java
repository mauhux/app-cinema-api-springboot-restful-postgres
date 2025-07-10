package dev.mauhux.apps.cinema.business.api.resources;

import dev.mauhux.apps.cinema.business.api.dtos.CustomerRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.CustomerResponseDto;
import dev.mauhux.apps.cinema.business.domain.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Customers", description = "REST API for managing customers")
public class CustomerResource {

    private CustomerService customerService;

    @Operation(
            summary = "Get all customers",
            description = "Allows querying all registered customers",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List obtained successfully.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No customers registered."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error."
                    )
            },
            operationId = "getCustomers"
    )

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getCustomers() {
        try {
            List<CustomerResponseDto> customers = customerService.findAllCustomers();
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            log.error("Error getting customers.", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Get customer by ID",
            description = "Fetches a customer by their unique identifier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customer found successfully.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No customer found with the given ID."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error."
                    )
            },
            operationId = "getCustomerById"
    )

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable UUID id) {
        try {
            Optional<CustomerResponseDto> customer = customerService.findCustomerById(id);
            return customer
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.noContent().build());
        } catch (Exception e) {
            log.error("Error getting customer by id {}.", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Create a new customer",
            description = "Creates a new customer with the provided details",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Customer created successfully.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error."
                    )
            },
            operationId = "createCustomer"
    )

    @PostMapping
    public ResponseEntity<CustomerResponseDto> create(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        try {
            CustomerResponseDto customerResponseDto = customerService.createCustomer(customerRequestDto);
            URI location = URI.create(String.format("/customers/%s", customerResponseDto.id()));
            return ResponseEntity
                    .created(location)
                    .body(customerResponseDto);
        } catch (Exception e) {
            log.error("Error creating customer.", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Update an existing customer",
            description = "Updates the details of an existing customer identified by the provided ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customer updated successfully.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Customer not found with the given ID."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error."
                    )
            },
            operationId = "updateCustomer"
    )

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> update(@Valid @RequestBody CustomerRequestDto customerRequestDto,
                                                      @PathVariable UUID id) {
        try {
            CustomerResponseDto customerResponseDto = customerService.updateCustomer(customerRequestDto, id);
            return ResponseEntity.ok(customerResponseDto);
        } catch (Exception e) {
            log.error("Error updating customer.", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Delete a customer by ID",
            description = "Deletes the customer identified by the provided ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customer deleted successfully."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Customer not found with the given ID."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error."
                    )
            },
            operationId = "deleteCustomer"
    )

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
