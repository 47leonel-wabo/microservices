package com.wbt.orderservice.dto;

import com.wbt.orderservice.dto.product.ProductRequestDto;
import com.wbt.orderservice.dto.user.UserTransactionRequestDto;
import com.wbt.orderservice.dto.user.UserTransactionResponseDto;

public record RequestContext(
        OrderRequestDto orderRequestDto,
        ProductRequestDto productRequestDto,
        UserTransactionRequestDto transactionRequestDto,
        UserTransactionResponseDto transactionResponseDto
) {
}
