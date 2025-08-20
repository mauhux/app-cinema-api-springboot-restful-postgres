package dev.mauhux.apps.cinema.business.api.resources;

import dev.mauhux.apps.cinema.business.api.dtos.CinemaRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaResponseDto;
import dev.mauhux.apps.cinema.business.domain.services.CinemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/cinemas")
@AllArgsConstructor
@Tag(name = "Cinemas", description = "REST API for managing cinemas")
public class CinemaResource {

    private final CinemaService cinemaService;

    @PostMapping
    @Operation(summary = "Create a new cinema", description = "Registers a new cinema in the system.")
    public ResponseEntity<CinemaResponseDto> createCinema(
            @Valid @RequestBody CinemaRequestDto dto) {
        var created = cinemaService.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @Operation(summary = "List all cinemas (paginated)", description = "Returns all registered cinemas with pagination support.")
    public ResponseEntity<Page<CinemaResponseDto>> getAllCinemas(
            @Parameter(description = "Pagination and sorting options") Pageable pageable) {
        var cinemas = cinemaService.getCinemas(pageable);
        return ResponseEntity.ok(cinemas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cinema by ID", description = "Retrieves a cinema by its UUID.")
    public ResponseEntity<CinemaResponseDto> getCinemaById(
            @Parameter(description = "UUID of the cinema") @PathVariable UUID id) {
        var cinema = cinemaService.getCinemaById(id);
        return ResponseEntity.ok(cinema);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cinema", description = "Updates the information of a cinema using its ID.")
    public ResponseEntity<CinemaResponseDto> updateCinema(
            @Parameter(description = "UUID of the cinema to update") @PathVariable UUID id,
            @Valid @RequestBody CinemaRequestDto dto) {
        var updated = cinemaService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cinema", description = "Deletes a cinema by its ID.")
    public ResponseEntity<Void> deleteCinema(
            @Parameter(description = "UUID of the cinema to delete") @PathVariable UUID id) {
        cinemaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
