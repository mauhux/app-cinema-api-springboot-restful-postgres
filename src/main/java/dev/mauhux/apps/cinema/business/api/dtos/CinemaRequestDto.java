package dev.mauhux.apps.cinema.business.api.dtos;

import dev.mauhux.apps.cinema.business.domain.enums.CinemaStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record CinemaRequestDto(

        @NotBlank(message = "District ID is required.")
        String districtId,

        @NotBlank(message = "Cinema name is required.")
        @Size(max = 100, message = "Cinema name must be at most 100 characters.")
        String cinemaName,

        @NotBlank(message = "Address is required.")
        @Size(max = 100, message = "Address must be at most 100 characters.")
        String address,

        @Pattern(regexp = "\\d{9}", message = "Phone number must be 9 digits.")
        String phoneNumber,

        @Email(message = "Invalid email format.")
        String contactEmail,

        String imageUrl,

        @NotNull(message = "Cinema status is required.")
        CinemaStatus status,

        LocalDate openingDate,

        LocalTime openingTime,

        LocalTime closingTime
) {
}
