package io.github.mrgsrylm.store.logging.service;

import io.github.mrgsrylm.store.logging.entity.LogEntity;

/**
 * Service interface for saving {@link LogEntity} to the database.
 */
public interface LogService {
    void saveLogToDatabase(LogEntity logEntity);
}
