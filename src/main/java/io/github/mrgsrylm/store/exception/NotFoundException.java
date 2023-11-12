package io.github.mrgsrylm.store.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * The abstract base class for exceptions indicating that a requested resource could not be found.
 *
 * <p>
 * This class extends {@code RuntimeException} and provides a custom HTTP status code ({@code HttpStatus.NOT_FOUND})
 * to represent the error condition.
 * </p>
 *
 * <p>
 * Subclasses of this class should be created to represent specific resource types and situations where the
 * "not found" condition may occur. They should provide meaningful error messages and handle the exception
 * appropriately in the application's error handling logic.
 * </p>
 *
 * @serial 1L
 * @see RuntimeException
 * @see org.springframework.http.HttpStatus
 */
public abstract class NotFoundException extends RuntimeException {
    public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a {@code NotFoundException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    protected NotFoundException(String message) {
        super(message);
    }
}

