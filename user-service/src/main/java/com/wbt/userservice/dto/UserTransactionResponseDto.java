package com.wbt.userservice.dto;

public record UserTransactionResponseDto(Long userId, Double amount, TransactionStatus status) {
}
