package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDeliveryInfoDto {
    private Long orderId;
    private Long userId;
    private String address;
}

