package dev.mauhux.apps.cinema.business.data.repositories;

import dev.mauhux.apps.cinema.business.data.model.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
}
