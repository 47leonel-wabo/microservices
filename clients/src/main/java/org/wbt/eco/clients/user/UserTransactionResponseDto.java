package org.wbt.eco.clients.user;

public record UserTransactionResponseDto(Long userId, Double amount, TransactionStatus status) {
}
