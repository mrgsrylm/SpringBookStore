package io.github.mrgsrylm.store.controller;

import io.github.mrgsrylm.store.dto.OrderDTO;
import io.github.mrgsrylm.store.model.mapper.order.OrderMapper;
import io.github.mrgsrylm.store.payload.request.order.CreateOrderRequest;
import io.github.mrgsrylm.store.payload.request.pagination.PaginatedFindAllRequest;
import io.github.mrgsrylm.store.payload.request.pagination.PaginationRequest;
import io.github.mrgsrylm.store.payload.response.CustomPageResponse;
import io.github.mrgsrylm.store.payload.response.CustomResponse;
import io.github.mrgsrylm.store.payload.response.order.OrderCreatedResponse;
import io.github.mrgsrylm.store.payload.response.order.OrderGetBetweenDatesResponse;
import io.github.mrgsrylm.store.payload.response.order.OrderGetByCustomerResponse;
import io.github.mrgsrylm.store.payload.response.order.OrderGetResponse;
import io.github.mrgsrylm.store.service.OrderSaveService;
import io.github.mrgsrylm.store.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    private final OrderService orderService;
    private final OrderSaveService orderSaveService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<OrderCreatedResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {

        final OrderDTO orderDTO = orderSaveService.createOrder(createOrderRequest);
        final OrderCreatedResponse response = OrderMapper.toCreatedResponse(orderDTO);
        return CustomResponse.created(response);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')")
    public CustomResponse<OrderGetResponse> getOrderById(@PathVariable Long orderId) {

        final OrderDTO orderDTO = orderService.findOrderById(orderId);
        final OrderGetResponse response = OrderMapper.toGetResponse(orderDTO);
        return CustomResponse.ok(response);

    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')")
    public CustomResponse<CustomPageResponse<OrderGetByCustomerResponse>> getOrdersByCustomerId(
            @PathVariable Long customerId,
            @RequestBody PaginationRequest paginationRequest
    ) {

        final Page<OrderDTO> pageOfOrderDTOs = orderService
                .findAllOrdersByCustomerId(customerId, paginationRequest);

        final CustomPageResponse<OrderGetByCustomerResponse> response = OrderMapper
                .toGetByCustomerResponse(pageOfOrderDTOs);
        return CustomResponse.ok(response);

    }

    @PostMapping("/between-dates")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomResponse<CustomPageResponse<OrderGetBetweenDatesResponse>> getOrdersBetweenTwoDates(
            @RequestBody PaginatedFindAllRequest paginatedFindAllRequest
    ) {
        final Page<OrderDTO> pageOfOrderDTOs = orderService
                .findAllOrdersBetweenTwoDatesAndPagination(paginatedFindAllRequest);
        final CustomPageResponse<OrderGetBetweenDatesResponse> response = OrderMapper
                .toGetBetweenDatesResponses(pageOfOrderDTOs);

        return CustomResponse.ok(response);
    }
}
