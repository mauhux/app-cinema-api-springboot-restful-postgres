package dev.mauhux.apps.cinema.business.api.dtos;

import lombok.Builder;

@Builder
public record DepartmentResponseDto(
        String id,
        String name
) {
}
