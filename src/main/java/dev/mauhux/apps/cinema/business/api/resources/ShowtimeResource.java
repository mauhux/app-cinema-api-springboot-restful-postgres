package dev.mauhux.apps.cinema.business.api.resources;

import dev.mauhux.apps.cinema.business.api.dtos.ShowtimeRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.ShowtimeResponseDto;
import dev.mauhux.apps.cinema.business.domain.services.ShowtimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/showtimes")
@AllArgsConstructor
@Tag(name = "Showtimes", description = "CRUD operations for showtimes")
public class ShowtimeResource {

    private final ShowtimeService showtimeService;

    @PostMapping
    @Operation(summary = "Create a new showtime", description = "Registers a new showtime with movie and cinema data.")
    public ResponseEntity<ShowtimeResponseDto> createShowtime(
            @Valid @RequestBody ShowtimeRequestDto requestDto) {
        var created = showtimeService.create(requestDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @Operation(summary = "Get all showtimes (paginated)", description = "Retrieves a paginated list of all showtimes.")
    public ResponseEntity<org.springframework.data.domain.Page<ShowtimeResponseDto>> getAllShowtimes(
            @Parameter(description = "Pagination and sorting options") Pageable pageable) {
        var showtimes = showtimeService.findAll(pageable);
        return ResponseEntity.ok(showtimes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a showtime by ID", description = "Fetches a showtime using its unique identifier.")
    public ResponseEntity<ShowtimeResponseDto> getShowtimeById(
            @Parameter(description = "UUID of the showtime") @PathVariable UUID id) {
        var showtime = showtimeService.findById(id);
        return ResponseEntity.ok(showtime);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a showtime", description = "Updates an existing showtime by its ID.")
    public ResponseEntity<ShowtimeResponseDto> updateShowtime(
            @Parameter(description = "UUID of the showtime to update") @PathVariable UUID id,
            @Valid @RequestBody ShowtimeRequestDto requestDto) {
        var updated = showtimeService.update(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a showtime", description = "Deletes a showtime using its unique ID.")
    public ResponseEntity<Void> deleteShowtime(
            @Parameter(description = "UUID of the showtime to delete") @PathVariable UUID id) {
        showtimeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
