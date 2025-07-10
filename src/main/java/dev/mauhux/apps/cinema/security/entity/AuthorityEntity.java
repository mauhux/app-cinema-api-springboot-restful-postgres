package dev.mauhux.apps.cinema.security.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "auth_authorities")
@Data
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Integer id;

    @Column(name = "authority_name")
    private String authorityName;
}
