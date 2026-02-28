package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPaidEventDto {
    private Long orderId;
    private Long paymentId;
}

