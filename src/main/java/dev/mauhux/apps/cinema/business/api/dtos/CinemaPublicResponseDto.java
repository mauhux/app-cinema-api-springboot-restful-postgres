package dev.mauhux.apps.cinema.business.api.dtos;

import java.time.LocalTime;
import java.util.UUID;

public record CinemaPublicResponseDto(

        UUID id,
        String cinemaName,
        String address,
        String phoneNumber,
        String imageUrl,
        LocalTime openingTime,
        LocalTime closingTime
) {
}
