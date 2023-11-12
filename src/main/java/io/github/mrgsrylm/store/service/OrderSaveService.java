package io.github.mrgsrylm.store.service;

import io.github.mrgsrylm.store.dto.OrderDTO;
import io.github.mrgsrylm.store.payload.request.order.CreateOrderRequest;

/**
 * This interface defines a service for creating and managing orders.
 */
public interface OrderSaveService {
    /**
     * Creates a new order based on the provided create order request.
     *
     * @param createOrderRequest The request containing order information to be used for creation.
     * @return An {@link OrderDTO} representing the newly created order.
     */
    OrderDTO createOrder(CreateOrderRequest createOrderRequest);
}
