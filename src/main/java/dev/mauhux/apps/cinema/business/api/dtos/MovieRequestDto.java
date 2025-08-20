package dev.mauhux.apps.cinema.business.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MovieRequestDto(
        @NotBlank(message = "Title is required.")
        String title,

        @NotBlank(message = "Genre is required.")
        String genre,

        @NotNull(message = "Duration is required.")
        @Min(value = 1, message = "Duration must be greater than 0.")
        Integer durationMinutes,

        String description
) {
}
