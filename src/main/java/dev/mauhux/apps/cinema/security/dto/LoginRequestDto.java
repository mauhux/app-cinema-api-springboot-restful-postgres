package dev.mauhux.apps.cinema.security.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record LoginRequestDto(
        @NotEmpty(message = "Username is required")
        String username,

        @NotEmpty(message = "Password is required")
        String password
) {
}
