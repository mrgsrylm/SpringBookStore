package io.github.mrgsrylm.store.service.impl;

import io.github.mrgsrylm.store.dto.OrderDTO;
import io.github.mrgsrylm.store.dto.OrderItemDTO;
import io.github.mrgsrylm.store.exception.user.UserNotFoundException;
import io.github.mrgsrylm.store.model.Order;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.model.mapper.order.OrderItemMapper;
import io.github.mrgsrylm.store.model.mapper.order.OrderMapper;
import io.github.mrgsrylm.store.payload.request.order.CreateOrderRequest;
import io.github.mrgsrylm.store.repository.OrderRepository;
import io.github.mrgsrylm.store.security.CustomUserDetails;
import io.github.mrgsrylm.store.service.OrderItemService;
import io.github.mrgsrylm.store.service.OrderSaveService;
import io.github.mrgsrylm.store.service.UserService;
import io.github.mrgsrylm.store.util.Identity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link OrderSaveService} interface for creating and managing orders.
 */
@Service
@RequiredArgsConstructor
public class OrderSaveServiceImpl implements OrderSaveService {
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final Identity identity;

    /**
     * Creates a new order based on the provided create order request.
     *
     * @param createOrderRequest The request containing order information to be used for creation.
     * @return An {@link OrderDTO} representing the newly created order.
     */
    @Transactional
    @Override
    public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {

        CustomUserDetails customUserDetails = identity.getCustomUserDetails();

        User user = userService.findByEmail(customUserDetails.getEmail())
                .orElseThrow(() -> new UserNotFoundException(customUserDetails.getId()));

        List<OrderItemDTO> orderItemDTOs = createOrderRequest
                .getOrderDetailSet()
                .stream()
                .map(orderItemService::createOrderItem)
                .toList();

        Order order = Order.builder()
                .user(user)
                .build();

        order.setOrderItems(OrderItemMapper.toOrderItem(orderItemDTOs));

        return OrderMapper.toOrderDTO(orderRepository.save(order));

    }
}
