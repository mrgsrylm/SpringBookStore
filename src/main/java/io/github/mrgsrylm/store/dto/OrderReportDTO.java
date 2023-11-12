package io.github.mrgsrylm.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) representing order report information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReportDTO {
    private String month;
    private Integer year;
    private Long totalOrderCount;
    private Long totalBookCount;
    private BigDecimal totalPrice;
}
