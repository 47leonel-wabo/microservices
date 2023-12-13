package com.wbt.orderservice.dto;

import com.wbt.orderservice.util.OrderStatus;

public record OrderResponseDto(
        Long orderId,
        Long userId,
        String productId,
        Double charge,
        OrderStatus status
) {
}
