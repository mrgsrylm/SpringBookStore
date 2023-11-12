package io.github.mrgsrylm.store.controller;

import io.github.mrgsrylm.store.base.BaseControllerTest;
import io.github.mrgsrylm.store.dto.OrderReportDTO;
import io.github.mrgsrylm.store.model.mapper.order.OrderReportMapper;
import io.github.mrgsrylm.store.payload.request.pagination.PaginationRequest;
import io.github.mrgsrylm.store.payload.response.CustomPageResponse;
import io.github.mrgsrylm.store.payload.response.CustomResponse;
import io.github.mrgsrylm.store.payload.response.order.OrderReportResponse;
import io.github.mrgsrylm.store.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StatisticsControllerTest extends BaseControllerTest {
    @MockBean
    private StatisticsService statisticsService;

    @Test
    void givenCustomerIdAndPaginationRequest_WhenCustomerRole_ReturnOrderReportResponse() throws Exception {

        // Given
        Long customerId = 1L;

        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .build();

        OrderReportDTO orderReportDTO = OrderReportDTO.builder()
                .month(Month.SEPTEMBER.name())
                .year(2023)
                .totalOrderCount(100L)
                .totalBookCount(200L)
                .totalPrice(BigDecimal.valueOf(1200.90))
                .build();
        // When
        Page<OrderReportDTO> pageOfOrderReportDTOs = new PageImpl<>(Collections.singletonList(orderReportDTO));

        when(statisticsService.getOrderStatisticsByCustomerId(customerId, paginationRequest)).thenReturn(pageOfOrderReportDTOs);

        // Then
        CustomPageResponse<OrderReportResponse> customPageResponse = OrderReportMapper.toOrderReportResponseList(pageOfOrderReportDTOs);
        CustomResponse<CustomPageResponse<OrderReportResponse>> expectedResponse = CustomResponse.ok(customPageResponse);

        mockMvc.perform(get("/api/v1/statistics/{customerId}", customerId)
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .content(objectMapper.writeValueAsString(paginationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.content[0].month").value(orderReportDTO.getMonth()))
                .andExpect(jsonPath("$.response.content[0].year").value(orderReportDTO.getYear()))
                .andExpect(jsonPath("$.response.content[0].totalOrderCount").value(orderReportDTO.getTotalOrderCount()))
                .andExpect(jsonPath("$.response.content[0].totalBookCount").value(orderReportDTO.getTotalBookCount()))
                .andExpect(jsonPath("$.isSuccess").value(expectedResponse.getIsSuccess()))
                .andExpect(jsonPath("$.httpStatus").value(expectedResponse.getHttpStatus().name()))
                .andExpect(jsonPath("$.time").isNotEmpty());
    }

    @Test
    void givenCustomerIdAndPaginationRequest_WhenAdminRole_ReturnOrderReportResponse() throws Exception {

        // Given
        Long customerId = 1L;

        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .build();

        OrderReportDTO orderReportDTO = OrderReportDTO.builder()
                .month(Month.SEPTEMBER.name())
                .year(2023)
                .totalOrderCount(100L)
                .totalBookCount(200L)
                .totalPrice(BigDecimal.valueOf(1200.90))
                .build();
        // When
        Page<OrderReportDTO> pageOfOrderReportDTOs = new PageImpl<>(Collections.singletonList(orderReportDTO));

        when(statisticsService.getOrderStatisticsByCustomerId(customerId, paginationRequest)).thenReturn(pageOfOrderReportDTOs);

        // Then
        CustomPageResponse<OrderReportResponse> customPageResponse = OrderReportMapper.toOrderReportResponseList(pageOfOrderReportDTOs);
        CustomResponse<CustomPageResponse<OrderReportResponse>> expectedResponse = CustomResponse.ok(customPageResponse);

        mockMvc.perform(get("/api/v1/statistics/{customerId}", customerId)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .content(objectMapper.writeValueAsString(paginationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.content[0].month").value(orderReportDTO.getMonth()))
                .andExpect(jsonPath("$.response.content[0].year").value(orderReportDTO.getYear()))
                .andExpect(jsonPath("$.response.content[0].totalOrderCount").value(orderReportDTO.getTotalOrderCount()))
                .andExpect(jsonPath("$.response.content[0].totalBookCount").value(orderReportDTO.getTotalBookCount()))
                .andExpect(jsonPath("$.isSuccess").value(expectedResponse.getIsSuccess()))
                .andExpect(jsonPath("$.httpStatus").value(expectedResponse.getHttpStatus().name()))
                .andExpect(jsonPath("$.time").isNotEmpty());
    }

    @Test
    void givenPaginationRequest_WhenAdminRole_ReturnOrderReportResponse() throws Exception {

        // Given
        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .build();

        OrderReportDTO orderReportDTO = OrderReportDTO.builder()
                .month(Month.SEPTEMBER.name())
                .year(2023)
                .totalOrderCount(100L)
                .totalBookCount(200L)
                .totalPrice(BigDecimal.valueOf(1200.90))
                .build();
        // When
        Page<OrderReportDTO> pageOfOrderReportDTOs = new PageImpl<>(Collections.singletonList(orderReportDTO));

        when(statisticsService.getAllOrderStatistics(paginationRequest)).thenReturn(pageOfOrderReportDTOs);

        // Then
        CustomPageResponse<OrderReportResponse> customPageResponse = OrderReportMapper.toOrderReportResponseList(pageOfOrderReportDTOs);
        CustomResponse<CustomPageResponse<OrderReportResponse>> expectedResponse = CustomResponse.ok(customPageResponse);

        mockMvc.perform(get("/api/v1/statistics")
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .content(objectMapper.writeValueAsString(paginationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.content[0].month").value(orderReportDTO.getMonth()))
                .andExpect(jsonPath("$.response.content[0].year").value(orderReportDTO.getYear()))
                .andExpect(jsonPath("$.response.content[0].totalOrderCount").value(orderReportDTO.getTotalOrderCount()))
                .andExpect(jsonPath("$.response.content[0].totalBookCount").value(orderReportDTO.getTotalBookCount()))
                .andExpect(jsonPath("$.isSuccess").value(expectedResponse.getIsSuccess()))
                .andExpect(jsonPath("$.httpStatus").value(expectedResponse.getHttpStatus().name()))
                .andExpect(jsonPath("$.time").isNotEmpty());
    }
}