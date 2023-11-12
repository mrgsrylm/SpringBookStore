package io.github.mrgsrylm.store.model.mapper.customer;

import io.github.mrgsrylm.store.dto.UserDTO;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.payload.response.customer.CustomerCreatedResponse;
import lombok.experimental.UtilityClass;

/**
 * Utility class for mapping operations related to customer data.
 */
@UtilityClass
public class CustomerMapper {
    /**
     * Converts a {@link User} object to a {@link CustomerCreatedResponse}.
     *
     * @param source The source {@link User} object to be converted.
     * @return A {@link CustomerCreatedResponse} containing data from the source object.
     */
    public static CustomerCreatedResponse toCreatedResponse(UserDTO source) {

        if (source == null) {
            return null;
        }

        return CustomerCreatedResponse.builder()
                .id(source.getId())
                .fullName(source.getFullName())
                .username(source.getUsername())
                .email(source.getEmail())
                .build();
    }
}

