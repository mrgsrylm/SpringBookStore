package io.github.mrgsrylm.store.service;

import io.github.mrgsrylm.store.dto.OrderItemDTO;
import io.github.mrgsrylm.store.payload.request.order.OrderItemRequest;

/**
 * This interface defines a service for managing order items within an order.
 */
public interface OrderItemService {
    /**
     * Creates a new order item based on the provided order item request.
     *
     * @param orderDetailRequest The request containing order item information to be used for creation.
     * @return An {@link OrderItemDTO} representing the newly created order item.
     */
    OrderItemDTO createOrderItem(OrderItemRequest orderDetailRequest);
}
