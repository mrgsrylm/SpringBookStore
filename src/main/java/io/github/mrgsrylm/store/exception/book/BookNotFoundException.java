package io.github.mrgsrylm.store.exception.book;

import io.github.mrgsrylm.store.exception.NotFoundException;
import io.github.mrgsrylm.store.model.Book;

import java.io.Serial;

/**
 * Thrown when the specified {@link Book} is not found.
 */
public class BookNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE =
            "The specified Book is not found!";

    private static final String MESSAGE_TEMPLATE =
            "No book found with ID: ";

    public BookNotFoundException(String id) {
        super(MESSAGE_TEMPLATE.concat(id));
    }

    public BookNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
