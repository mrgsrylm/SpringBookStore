package io.github.mrgsrylm.store.exception.order;

import io.github.mrgsrylm.store.exception.NotFoundException;

import java.io.Serial;

public class OrderNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE =
            "Order Not Found!";

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
