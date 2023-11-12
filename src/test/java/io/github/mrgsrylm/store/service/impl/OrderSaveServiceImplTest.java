package io.github.mrgsrylm.store.service.impl;

import io.github.mrgsrylm.store.base.BaseServiceTest;
import io.github.mrgsrylm.store.builder.BookBuilder;
import io.github.mrgsrylm.store.builder.UserBuilder;
import io.github.mrgsrylm.store.dto.OrderDTO;
import io.github.mrgsrylm.store.dto.OrderItemDTO;
import io.github.mrgsrylm.store.model.Order;
import io.github.mrgsrylm.store.model.OrderItem;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.model.mapper.order.OrderItemMapper;
import io.github.mrgsrylm.store.model.mapper.order.OrderMapper;
import io.github.mrgsrylm.store.payload.request.order.CreateOrderRequest;
import io.github.mrgsrylm.store.payload.request.order.OrderItemRequest;
import io.github.mrgsrylm.store.repository.OrderRepository;
import io.github.mrgsrylm.store.security.CustomUserDetails;
import io.github.mrgsrylm.store.service.OrderItemService;
import io.github.mrgsrylm.store.service.UserService;
import io.github.mrgsrylm.store.util.Identity;
import io.github.mrgsrylm.store.util.RandomUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

class OrderSaveServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private OrderSaveServiceImpl orderSaveService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private UserService userService;

    @Mock
    private Identity identity;

    @Test
    void givenValidCreateOrderRequest_whenOrderCreated_thenReturnOrderDTO() {

        // Given
        String bookId1 = RandomUtil.generateUUID();
        String bookId2 = RandomUtil.generateUUID();

        User user = new UserBuilder().customer().build();
        CustomUserDetails userDetails = new CustomUserDetails(user);

        OrderItemRequest mockOrderItemRequest1 = OrderItemRequest.builder()
                .bookId(bookId1)
                .amount(RandomUtil.generateRandomInteger(0, 5))
                .build();

        OrderItem orderItem1 = OrderItem.builder()
                .id(1L)
                .book(new BookBuilder().withValidFields().build())
                .build();
        OrderItemDTO orderItemDTO1 = OrderItemMapper.toDTO(orderItem1);

        OrderItemRequest mockOrderItemRequest2 = OrderItemRequest.builder()
                .bookId(bookId2)
                .amount(RandomUtil.generateRandomInteger(0, 5))
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .id(2L)
                .book(new BookBuilder().withValidFields().build())
                .build();
        OrderItemDTO orderItemDTO2 = OrderItemMapper.toDTO(orderItem2);

        CreateOrderRequest mockCreateOrderRequest = CreateOrderRequest.builder()
                .orderDetailSet(new LinkedHashSet<>(List.of(mockOrderItemRequest1, mockOrderItemRequest2)))
                .build();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .orderItems(List.of(orderItem1, orderItem2))
                .build();

        OrderDTO expected = OrderMapper.toOrderDTO(order);

        // When
        Mockito.when(identity.getCustomUserDetails()).thenReturn(userDetails);
        Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(orderItemService.createOrderItem(mockOrderItemRequest1)).thenReturn(orderItemDTO1);
        Mockito.when(orderItemService.createOrderItem(mockOrderItemRequest2)).thenReturn(orderItemDTO2);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        // Then
        OrderDTO response = orderSaveService.createOrder(mockCreateOrderRequest);

        Assertions.assertEquals(expected, response);
        Mockito.verify(identity, Mockito.times(1)).getCustomUserDetails();
        Mockito.verify(orderItemService, Mockito.times(2)).createOrderItem(Mockito.any(OrderItemRequest.class));
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));
    }
}