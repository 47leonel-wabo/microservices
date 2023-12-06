package com.wbt.userservice.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Transaction(@Id Long id, Long userId, Double amount, LocalDateTime transactionDate) {
}
