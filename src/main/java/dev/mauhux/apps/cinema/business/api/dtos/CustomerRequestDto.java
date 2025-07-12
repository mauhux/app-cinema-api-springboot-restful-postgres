package dev.mauhux.apps.cinema.business.api.dtos;

import dev.mauhux.apps.cinema.business.domain.enums.DocumentType;
import dev.mauhux.apps.cinema.business.domain.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CustomerRequestDto(

        @NotBlank(message = "First name is required.")
        String firstName,

        @NotBlank(message = "Last name is required.")
        String lastName,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email should be valid.")
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min = 6, message = "Password must be at least 6 characters long.")
        String password,

        @NotNull(message = "Document type is required.")
        DocumentType documentType,

        @NotBlank(message = "Document number is required.")
        @Size(max = 12, message = "Document number must not exceed 12 characters.")
        String documentNumber,

        @NotNull(message = "Birth date is required.")
        @Past(message = "Birth date must be in the past.")
        LocalDate birthDate,

        @NotBlank(message = "Phone number is required.")
        @Pattern(regexp = "\\d{9}", message = "Phone number must be 9 digits.")
        String phoneNumber,

        @NotBlank(message = "District ID is required.")
        String districtId,

        @NotNull(message = "Favorite cinema is required.")
        UUID cinemaId,

        @NotNull(message = "Gender is required.")
        Gender gender
) {
}
