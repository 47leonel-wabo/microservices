package com.wbt.userservice.service;

import com.wbt.userservice.dto.UserTransactionRequestDto;
import com.wbt.userservice.dto.UserTransactionResponseDto;
import com.wbt.userservice.entity.Transaction;
import com.wbt.userservice.repository.TransactionRepository;
import com.wbt.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.wbt.userservice.dto.TransactionStatus.APPROVED;
import static com.wbt.userservice.dto.TransactionStatus.DECLINED;

@Service
public record TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {

    public Mono<UserTransactionResponseDto> create(final Mono<UserTransactionRequestDto> requestDtoMono) {
        return requestDtoMono.flatMap(
                // (1) update user balance
                dto -> this.userRepository.updateUserBalance(dto.userId(), dto.amount())
                        // (2) if done successfully proceed
                        .filter(aBoolean -> aBoolean)
                        // (3) create new transaction with no ID (e.i. 0) and create it
                        .map(aBoolean -> new Transaction(null, dto.userId(), dto.amount(), LocalDateTime.now()))
                        .flatMap(this.transactionRepository::save)
                        // (4) return an APPROVED transaction response dto
                        .map(transaction -> new UserTransactionResponseDto(transaction.userId(), transaction.amount(), APPROVED))
                        // (5) if it were impossible to perform (1), return response with status DECLINED
                        .defaultIfEmpty(new UserTransactionResponseDto(dto.userId(), dto.amount(), DECLINED))
        );
    }

}
