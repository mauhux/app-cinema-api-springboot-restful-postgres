package dev.mauhux.apps.cinema.business.api.dtos;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CustomerDto(
        UUID id,
        String firstName,
        String lastName,
        String documentType,
        String documentNumber,
        LocalDate birthDate,
        String gender,
        String phoneNumber,
        String address,
        String maritalStatus
) {
}
