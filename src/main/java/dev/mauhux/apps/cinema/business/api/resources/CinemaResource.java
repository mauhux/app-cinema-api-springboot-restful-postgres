package dev.mauhux.apps.cinema.business.api.resources;

import dev.mauhux.apps.cinema.business.api.dtos.CinemaAdminResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaPublicResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.CinemaRequestDto;
import dev.mauhux.apps.cinema.business.domain.services.CinemaService;
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
@RequestMapping("/cinemas")
@AllArgsConstructor
@Tag(name = "Cinemas", description = "REST API for managing cinemas")
public class CinemaResource {

    private final CinemaService cinemaService;

    // 🌐 PUBLIC ENDPOINT

    @Operation(
            summary = "Get all public cinemas",
            description = "Returns a public list of all active cinemas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CinemaPublicResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "No cinemas found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/public")
    public ResponseEntity<List<CinemaPublicResponseDto>> getAllPublicCinemas() {
        try {
            List<CinemaPublicResponseDto> cinemas = cinemaService.getPublicCinemas();
            if (cinemas.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(cinemas);
        } catch (Exception e) {
            log.error("Error getting public cinemas", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 🔐 ADMIN ENDPOINTS

    @Operation(
            summary = "Get all cinemas (admin)",
            description = "Returns the full list of cinemas with admin-level info",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CinemaAdminResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "No cinemas found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/admin")
    public ResponseEntity<List<CinemaAdminResponseDto>> getAllCinemasAdmin() {
        try {
            List<CinemaAdminResponseDto> cinemas = cinemaService.getAdminCinemas();
            if (cinemas.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(cinemas);
        } catch (Exception e) {
            log.error("Error getting admin cinemas", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Get cinema by ID",
            description = "Returns full admin-level cinema data by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cinema found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CinemaAdminResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "Cinema not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/admin/{id}")
    public ResponseEntity<CinemaAdminResponseDto> getCinemaById(@PathVariable UUID id) {
        try {
            Optional<CinemaAdminResponseDto> cinema = cinemaService.getCinemaById(id);
            return cinema.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
        } catch (Exception e) {
            log.error("Error getting cinema by ID {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Create new cinema",
            description = "Creates a new cinema (admin only)",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cinema created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CinemaAdminResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/admin")
    public ResponseEntity<CinemaAdminResponseDto> create(@Valid @RequestBody CinemaRequestDto dto) {
        try {
            CinemaAdminResponseDto created = cinemaService.createCinema(dto);
            URI location = URI.create(String.format("/cinemas/admin/%s", created.id()));
            return ResponseEntity.created(location).body(created);
        } catch (Exception e) {
            log.error("Error creating cinema", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Update existing cinema",
            description = "Updates an existing cinema (admin only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cinema updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CinemaAdminResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Cinema not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/admin/{id}")
    public ResponseEntity<CinemaAdminResponseDto> update(@PathVariable UUID id, @Valid @RequestBody CinemaRequestDto dto) {
        try {
            return ResponseEntity.ok(cinemaService.updateCinema(id, dto));
        } catch (Exception e) {
            log.error("Error updating cinema with ID {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Delete cinema",
            description = "Deletes a cinema by ID (admin only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cinema deleted"),
                    @ApiResponse(responseCode = "404", description = "Cinema not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            cinemaService.deleteCinema(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting cinema with ID {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
