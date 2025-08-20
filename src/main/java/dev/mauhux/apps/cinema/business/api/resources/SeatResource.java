package dev.mauhux.apps.cinema.business.api.resources;

import dev.mauhux.apps.cinema.business.api.dtos.SeatRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.SeatResponseDto;
import dev.mauhux.apps.cinema.business.domain.services.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/seats")
@AllArgsConstructor
@Tag(name = "Seats", description = "CRUD operations for cinema seats")
public class SeatResource {

    private final SeatService seatService;

    @PostMapping
    @Operation(summary = "Create a new seat", description = "Registers a new seat in a specific cinema room.")
    public ResponseEntity<SeatResponseDto> createSeat(
            @Valid @RequestBody SeatRequestDto seatRequestDto) {
        var seat = seatService.create(seatRequestDto);
        return ResponseEntity.ok(seat);
    }

    @GetMapping
    @Operation(summary = "Get all seats (paginated)", description = "Retrieves a paginated list of all seats.")
    public ResponseEntity<Page<SeatResponseDto>> getAllSeats(
            @ParameterObject Pageable pageable) {
        var seats = seatService.findAll(pageable);
        return ResponseEntity.ok(seats);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a seat by ID", description = "Retrieves a seat by its unique identifier.")
    public ResponseEntity<SeatResponseDto> getSeatById(
            @Parameter(description = "UUID of the seat") @PathVariable UUID id) {
        var seat = seatService.findById(id);
        return ResponseEntity.ok(seat);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a seat", description = "Updates the data of an existing seat.")
    public ResponseEntity<SeatResponseDto> updateSeat(
            @Parameter(description = "UUID of the seat to update") @PathVariable UUID id,
            @Valid @RequestBody SeatRequestDto requestDto) {
        var updatedSeat = seatService.update(id, requestDto);
        return ResponseEntity.ok(updatedSeat);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a seat", description = "Deletes a seat by its unique ID.")
    public ResponseEntity<Void> deleteSeat(
            @Parameter(description = "UUID of the seat to delete") @PathVariable UUID id) {
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
