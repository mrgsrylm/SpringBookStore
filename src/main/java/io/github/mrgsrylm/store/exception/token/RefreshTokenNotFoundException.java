package io.github.mrgsrylm.store.exception.token;

import io.github.mrgsrylm.store.exception.NotFoundException;

import java.io.Serial;

public class RefreshTokenNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE =
            "The specified Refresh Token is not found!";

    public RefreshTokenNotFoundException(String message) {
        super(message);
    }

    public RefreshTokenNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}

