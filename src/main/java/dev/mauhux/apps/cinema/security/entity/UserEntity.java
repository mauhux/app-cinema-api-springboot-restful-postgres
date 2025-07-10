package dev.mauhux.apps.cinema.security.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "auth_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "username")
    private String userName;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Many to Many
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "auth_user_authorities",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private Set<AuthorityEntity> authorities = new HashSet<>();
}
