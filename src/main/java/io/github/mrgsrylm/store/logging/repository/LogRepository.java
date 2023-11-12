package io.github.mrgsrylm.store.logging.repository;

import io.github.mrgsrylm.store.logging.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the {@link LogEntity} class.
 */
public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
