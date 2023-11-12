package io.github.mrgsrylm.store.logging.service.impl;

import io.github.mrgsrylm.store.logging.entity.LogEntity;
import io.github.mrgsrylm.store.logging.repository.LogRepository;
import io.github.mrgsrylm.store.logging.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementation of the {@link LogService} interface for saving log entries to the database.
 */
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    /**
     * Save a {@link LogEntity} entry to the database.
     *
     * @param logEntity The {@link LogEntity} entity to be saved.
     */
    @Override
    public void saveLogToDatabase(LogEntity logEntity) {
        logEntity.setTime(LocalDateTime.now());
        logRepository.save(logEntity);
    }
}
