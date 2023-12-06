package com.wbt.userservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "user_transaction")
public record Transaction(@Id Long id, Long userId, Double amount, LocalDateTime transactionDate) {
}
