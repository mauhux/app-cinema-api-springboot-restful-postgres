package dev.mauhux.apps.cinema.repository;

import dev.mauhux.apps.cinema.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
}
