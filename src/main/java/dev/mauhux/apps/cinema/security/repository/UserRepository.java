package dev.mauhux.apps.cinema.security.repository;

import dev.mauhux.apps.cinema.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserNameIgnoreCase(String user);
}
