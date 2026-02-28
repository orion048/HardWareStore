package com.project.dto;

import com.hardwarestore.common.order.OrderStatus;
import lombok.Data;

@Data
public class OrderUpdateStatusRequest {
    private OrderStatus status;
}
