package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCreatedEventDto {
    private Long orderId;
    private Long userId;
    private String status;
}
