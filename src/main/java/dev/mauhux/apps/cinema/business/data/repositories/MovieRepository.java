package dev.mauhux.apps.cinema.business.data.repositories;

import dev.mauhux.apps.cinema.business.data.model.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {
}
