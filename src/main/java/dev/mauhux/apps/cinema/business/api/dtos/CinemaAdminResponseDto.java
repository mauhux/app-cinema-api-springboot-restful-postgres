package dev.mauhux.apps.cinema.business.api.dtos;

import dev.mauhux.apps.cinema.business.domain.enums.CinemaStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record CinemaAdminResponseDto(

        UUID id,
        DistrictResponseDto district,
        String cinemaName,
        String address,
        String phoneNumber,
        String contactEmail,
        String imageUrl,
        CinemaStatus status,
        LocalDate openingDate,
        LocalTime openingTime,
        LocalTime closingTime,
        LocalDateTime createdAt
) {
}
