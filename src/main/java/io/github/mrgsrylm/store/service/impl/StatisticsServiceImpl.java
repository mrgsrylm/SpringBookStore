package io.github.mrgsrylm.store.service.impl;

import io.github.mrgsrylm.store.dto.OrderReportDTO;
import io.github.mrgsrylm.store.model.enums.Role;
import io.github.mrgsrylm.store.payload.request.pagination.PaginationRequest;
import io.github.mrgsrylm.store.repository.OrderRepository;
import io.github.mrgsrylm.store.security.CustomUserDetails;
import io.github.mrgsrylm.store.service.StatisticsService;
import io.github.mrgsrylm.store.util.Identity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link StatisticsService} interface for retrieving order statistics.
 */
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final OrderRepository orderRepository;
    private final Identity identity;

    /**
     * Retrieves order statistics for a specific customer.
     *
     * @param customerId        The unique identifier of the customer.
     * @param paginationRequest The request containing pagination information.
     * @return A {@link Page} of {@link OrderReportDTO} objects representing order statistics for the customer.
     */
    @Override
    public Page<OrderReportDTO> getOrderStatisticsByCustomerId(Long customerId, PaginationRequest paginationRequest) {

        final CustomUserDetails userDetails = identity.getCustomUserDetails();
        final Role userRole = userDetails.getUser().getRole();
        if ((userRole.equals(Role.ROLE_CUSTOMER) && userDetails.getId().equals(customerId))
                || userRole.equals(Role.ROLE_ADMIN)) {
            return orderRepository.findOrderStatisticsByCustomerId(customerId, paginationRequest.toPageable());
        }
        throw new AccessDeniedException("You cannot access order statistics");
    }

    /**
     * Retrieves overall order statistics.
     *
     * @param paginationRequest The request containing pagination information.
     * @return A {@link Page} of {@link OrderReportDTO} objects representing overall order statistics.
     */
    @Override
    public Page<OrderReportDTO> getAllOrderStatistics(PaginationRequest paginationRequest) {
        return orderRepository.findAllOrderStatistics(paginationRequest.toPageable());
    }
}
