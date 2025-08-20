package dev.mauhux.apps.cinema.business.api.dtos;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record MovieResponseDto(
        UUID id,
        String title,
        String genre,
        Integer durationMinutes,
        String description,
        LocalDateTime createdAt
) {
}
