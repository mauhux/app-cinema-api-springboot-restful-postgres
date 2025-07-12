package dev.mauhux.apps.cinema.business.data.model.entities;

import dev.mauhux.apps.cinema.business.domain.enums.CinemaStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "cinemas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private DistrictEntity district;

    @Column(name = "cinema_name", nullable = false, length = 100)
    private String cinemaName;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "phone_number", length = 9)
    private String phoneNumber;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CinemaStatus status;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
