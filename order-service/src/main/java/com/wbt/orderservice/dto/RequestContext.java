package com.wbt.orderservice.dto;

import com.wbt.orderservice.dto.product.ProductResponseDto;
import com.wbt.orderservice.dto.user.UserTransactionRequestDto;
import com.wbt.orderservice.dto.user.UserTransactionResponseDto;

public record RequestContext(OrderRequestDto orderRequestDto) {

    private static ProductResponseDto productResponseDto;
    private static UserTransactionRequestDto transactionRequestDto;
    private static UserTransactionResponseDto transactionResponseDto;

    public  ProductResponseDto getProductResponseDto() {
        return productResponseDto;
    }

    public void setProductResponseDto(ProductResponseDto productResponseDto) {
        RequestContext.productResponseDto = productResponseDto;
    }

    public UserTransactionRequestDto getTransactionRequestDto() {
        return transactionRequestDto;
    }

    public void setTransactionRequestDto(UserTransactionRequestDto transactionRequestDto) {
        RequestContext.transactionRequestDto = transactionRequestDto;
    }

    public UserTransactionResponseDto getTransactionResponseDto() {
        return transactionResponseDto;
    }

    public void setTransactionResponseDto(UserTransactionResponseDto transactionResponseDto) {
        RequestContext.transactionResponseDto = transactionResponseDto;
    }
}
