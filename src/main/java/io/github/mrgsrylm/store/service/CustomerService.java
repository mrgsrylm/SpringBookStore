package io.github.mrgsrylm.store.service;

import io.github.mrgsrylm.store.dto.UserDTO;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.payload.request.customer.CustomerCreateRequest;

/**
 * This interface defines a service for managing customer-related operations.
 */
public interface CustomerService {
    /**
     * Creates a new customer based on the provided customer creation request.
     *
     * @param customerCreateRequest The request containing customer information to be used for creation.
     * @return A {@link User} representing the newly created customer.
     */
    UserDTO createCustomer(CustomerCreateRequest customerCreateRequest);
}
