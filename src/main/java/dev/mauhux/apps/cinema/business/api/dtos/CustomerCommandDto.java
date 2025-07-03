package dev.mauhux.apps.cinema.business.api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CustomerCommandDto(
        @NotEmpty(message = "The 'first name' field cannot be empty")
        String firstName,

        @NotEmpty(message = "The 'last name' field cannot be empty")
        String lastName,

        @NotEmpty(message = "The 'document type' field cannot be empty")
        String documentType,

        @NotEmpty(message = "The 'document number' field cannot be empty")
        String documentNumber,

        @NotNull(message = "The 'birth date' field cannot be null")
        LocalDate birthDate,

        @NotEmpty(message = "The 'gender' field cannot be empty")
        String gender,

        @NotEmpty(message = "The 'phone number' field cannot be empty")
        String phoneNumber,

        @NotEmpty(message = "The 'address' field cannot be empty")
        String address,

        @NotEmpty(message = "The 'marital status' field cannot be empty")
        String maritalStatus
) {
}
