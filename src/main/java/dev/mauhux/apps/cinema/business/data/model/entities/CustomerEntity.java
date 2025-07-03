package dev.mauhux.apps.cinema.business.data.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 20)
    private String gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    @Column(name = "marital_status")
    private String maritalStatus;
}
