package dev.mauhux.apps.cinema.business.data.repositories;

import dev.mauhux.apps.cinema.business.data.model.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
}
