package dev.mauhux.apps.cinema.business.api.dtos;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CustomerResponseDto(
        UUID id,
        String firstName,
        String lastName,
        String documentType,
        String documentNumber,
        LocalDate birthDate,
        String phoneNumber,
        String gender,
        //String email,
        DistrictResponseDto district,
        CinemaPublicResponseDto favoriteCinema
) {
}
