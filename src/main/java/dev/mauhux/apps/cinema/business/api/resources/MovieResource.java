package dev.mauhux.apps.cinema.business.api.resources;

import dev.mauhux.apps.cinema.business.api.dtos.MovieRequestDto;
import dev.mauhux.apps.cinema.business.api.dtos.MovieResponseDto;
import dev.mauhux.apps.cinema.business.domain.services.MovieService;
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
@RequestMapping("/movies")
@AllArgsConstructor
@Tag(name = "Movies", description = "CRUD operations for movies")
public class MovieResource {

    private final MovieService movieService;

    @PostMapping
    @Operation(summary = "Create a new movie", description = "Creates a new movie with the given data.")
    public ResponseEntity<MovieResponseDto> createMovie(
            @Valid @RequestBody MovieRequestDto requestDto) {
        var createdMovie = movieService.create(requestDto);
        return ResponseEntity.ok(createdMovie);
    }

    @GetMapping
    @Operation(summary = "Get all movies (paginated)", description = "Retrieves a paginated list of all movies.")
    public ResponseEntity<Page<MovieResponseDto>> getAllMovies(
            @Parameter(description = "Pagination and sorting options") Pageable pageable) {
        var movies = movieService.findAll(pageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a movie by ID", description = "Fetches a movie using its unique identifier.")
    public ResponseEntity<MovieResponseDto> getMovieById(
            @Parameter(description = "UUID of the movie") @PathVariable UUID id) {
        var movie = movieService.findById(id);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a movie", description = "Updates an existing movie by its ID.")
    public ResponseEntity<MovieResponseDto> updateMovie(
            @Parameter(description = "UUID of the movie to update") @PathVariable UUID id,
            @Valid @RequestBody MovieRequestDto requestDto) {
        var updatedMovie = movieService.update(id, requestDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie", description = "Deletes a movie using its unique ID.")
    public ResponseEntity<Void> deleteMovie(
            @Parameter(description = "UUID of the movie to delete") @PathVariable UUID id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
