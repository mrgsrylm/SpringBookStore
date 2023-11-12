package io.github.mrgsrylm.store.payload.response.order;

import io.github.mrgsrylm.store.dto.OrderItemDTO;
import io.github.mrgsrylm.store.dto.UserDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a response object for order creation, containing order details.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedResponse {
    private Long id;
    private UserDTO user;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> orderItems;
}
