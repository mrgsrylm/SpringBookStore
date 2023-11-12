package io.github.mrgsrylm.store.exception.book;

import io.github.mrgsrylm.store.exception.ProcessException;

import java.io.Serial;

/**
 * Custom exception class for cases where there is no available stock.
 */
public class NoAvailableStockException extends ProcessException {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE =
            "No available stock!";

    private static final String MESSAGE_TEMPLATE =
            "No available stock for the given amount : ";

    /**
     * Constructs a NoAvailableStockException with a specific amount.
     *
     * @param amount The amount for which no stock is available.
     */
    public NoAvailableStockException(int amount) {
        super(MESSAGE_TEMPLATE.concat(String.valueOf(amount)));
    }

    public NoAvailableStockException() {
        super(DEFAULT_MESSAGE);
    }
}

