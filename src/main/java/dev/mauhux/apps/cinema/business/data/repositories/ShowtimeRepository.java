package dev.mauhux.apps.cinema.business.data.repositories;

import dev.mauhux.apps.cinema.business.data.model.entities.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, UUID> {
}
