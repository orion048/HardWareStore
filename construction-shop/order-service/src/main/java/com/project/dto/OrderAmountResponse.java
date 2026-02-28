package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderAmountResponse {
    private Long orderId;
    private BigDecimal totalAmount;
}
