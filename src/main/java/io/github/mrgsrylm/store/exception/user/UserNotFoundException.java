package io.github.mrgsrylm.store.exception.user;

import io.github.mrgsrylm.store.exception.NotFoundException;

import java.io.Serial;

public class UserNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE =
            "The specified user is not found";

    private static final String MESSAGE_TEMPLATE =
            "No user was found with ID: ";

    public UserNotFoundException(Long id) {
        super(MESSAGE_TEMPLATE.concat(String.valueOf(id)));
    }

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
