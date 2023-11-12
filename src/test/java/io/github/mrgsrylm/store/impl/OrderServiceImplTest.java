package io.github.mrgsrylm.store.service.impl;

import io.github.mrgsrylm.store.base.BaseServiceTest;
import io.github.mrgsrylm.store.builder.BookBuilder;
import io.github.mrgsrylm.store.builder.UserBuilder;
import io.github.mrgsrylm.store.dto.OrderDTO;
import io.github.mrgsrylm.store.model.Book;
import io.github.mrgsrylm.store.model.Order;
import io.github.mrgsrylm.store.model.OrderItem;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.model.mapper.order.OrderMapper;
import io.github.mrgsrylm.store.payload.request.pagination.DateIntervalRequest;
import io.github.mrgsrylm.store.payload.request.pagination.PaginatedFindAllRequest;
import io.github.mrgsrylm.store.payload.request.pagination.PaginationRequest;
import io.github.mrgsrylm.store.repository.OrderRepository;
import io.github.mrgsrylm.store.security.CustomUserDetails;
import io.github.mrgsrylm.store.util.Identity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Identity identity;


    @Test
    void givenOrderId_WhenOrderFound_ReturnOrderDTO() {

        // given
        Long orderId = 1L;

        User mockUser = new UserBuilder().customer().build();

        Book mockBook1 = new BookBuilder().withValidFields().build();

        Book mockBook2 = new BookBuilder().withValidFields().build();

        OrderItem orderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .user(mockUser)
                .orderItems(List.of(orderItem1, orderItem2))
                .build();

        OrderDTO expected = OrderMapper.toOrderDTO(mockOrder);

        // when
        when(identity.getCustomUserDetails()).thenReturn(new CustomUserDetails(mockUser));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        // then
        OrderDTO actual = orderService.findOrderById(orderId);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUser().getId(), actual.getUser().getId());
        assertEquals(expected.getUser().getFullName(), actual.getUser().getFullName());
        assertEquals(expected.getOrderItems().size(), actual.getOrderItems().size());
        assertEquals(expected.getOrderItems().size(), actual.getOrderItems().size());

        // verify
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void givenCustomerIdAndPaginationRequest_WhenOrdersFound_ReturnPageOrderDTOList() {

        // Given
        Long customerId = 1L;
        Long orderId = 1L;
        PaginationRequest paginationRequest = new PaginationRequest(0, 10);
        Pageable pageRequest = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());

        User mockUser = new UserBuilder().customer().withId(customerId).build();

        Book mockBook1 = new BookBuilder().withValidFields().build();
        Book mockBook2 = new BookBuilder().withValidFields().build();

        OrderItem orderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .user(mockUser)
                .orderItems(List.of(orderItem1, orderItem2))
                .build();

        // When
        when(identity.getCustomUserDetails()).thenReturn(new CustomUserDetails(mockUser));
        when(orderRepository.findAllByUserId(customerId, pageRequest))
                .thenReturn(new PageImpl<>(Collections.singletonList(mockOrder)));

        // Then
        Page<OrderDTO> result = orderService.findAllOrdersByCustomerId(customerId, paginationRequest);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        // verify
        verify(orderRepository, times(1)).findAllByUserId(customerId, pageRequest);
    }

    @Test
    void givenPaginatedFindAllRequest_WhenOrdersFound_ReturnPageOrderDTOList() {

        // Given
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 10);

        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 13);

        LocalDateTime startDate = calendar1.getTime().toInstant().atZone(calendar1.getTimeZone().toZoneId()).toLocalDateTime();
        LocalDateTime endDate = calendar2.getTime().toInstant().atZone(calendar2.getTimeZone().toZoneId()).toLocalDateTime();

        DateIntervalRequest dateIntervalRequest = new DateIntervalRequest(startDate, endDate);
        PaginationRequest paginationRequest = new PaginationRequest(0, 10);
        Pageable pageRequest = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());

        Long orderId = 1L;

        User mockUser = new UserBuilder().customer().build();


        Book mockBook1 = new BookBuilder().withValidFields().build();
        Book mockBook2 = new BookBuilder().withValidFields().build();

        OrderItem orderItem1 = OrderItem.builder()
                .book(mockBook1)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .book(mockBook2)
                .build();

        Order mockOrder = Order.builder()
                .id(orderId)
                .user(mockUser)
                .orderItems(List.of(orderItem1, orderItem2))
                .build();

        // When
        when(orderRepository.findAllByCreatedAtBetween(dateIntervalRequest.getStartDate(), dateIntervalRequest.getEndDate(), pageRequest))
                .thenReturn(new PageImpl<>(Collections.singletonList(mockOrder)));

        // Then
        Page<OrderDTO> result = orderService.findAllOrdersBetweenTwoDatesAndPagination(
                new PaginatedFindAllRequest(dateIntervalRequest, paginationRequest));
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        // verify
        verify(orderRepository, times(1)).findAllByCreatedAtBetween(dateIntervalRequest.getStartDate(), dateIntervalRequest.getEndDate(), pageRequest);
    }
}