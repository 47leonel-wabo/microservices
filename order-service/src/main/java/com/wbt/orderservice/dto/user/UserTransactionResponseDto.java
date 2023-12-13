package com.wbt.orderservice.dto.user;

public record UserTransactionResponseDto(Long userId, Double amount, TransactionStatus status) {
}
