package io.github.mrgsrylm.store.service;

import io.github.mrgsrylm.store.dto.OrderReportDTO;
import io.github.mrgsrylm.store.payload.request.pagination.PaginationRequest;
import org.springframework.data.domain.Page;

/**
 * This interface defines a service for retrieving order statistics.
 */
public interface StatisticsService {
    /**
     * Retrieves order statistics for a specific customer.
     *
     * @param customerId        The unique identifier of the customer.
     * @param paginationRequest The request containing pagination information.
     * @return A {@link Page} of {@link OrderReportDTO} objects representing order statistics for the customer.
     */
    Page<OrderReportDTO> getOrderStatisticsByCustomerId(Long customerId, PaginationRequest paginationRequest);

    /**
     * Retrieves overall order statistics.
     *
     * @param paginationRequest The request containing pagination information.
     * @return A {@link Page} of {@link OrderReportDTO} objects representing overall order statistics.
     */
    Page<OrderReportDTO> getAllOrderStatistics(PaginationRequest paginationRequest);
}
