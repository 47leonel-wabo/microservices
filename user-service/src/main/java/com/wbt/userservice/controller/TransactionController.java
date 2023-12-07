package com.wbt.userservice.controller;

import com.wbt.userservice.dto.UserTransactionRequestDto;
import com.wbt.userservice.dto.UserTransactionResponseDto;
import com.wbt.userservice.entity.Transaction;
import com.wbt.userservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/transactions"})
public record TransactionController(TransactionService transactionService) {
    @PostMapping
    Mono<UserTransactionResponseDto> perform(final @RequestBody Mono<UserTransactionRequestDto> requestDtoMono) {
        return this.transactionService().create(requestDtoMono);
    }

    @GetMapping(path = {"/user/{userId}"})
    public Flux<Transaction> getUserTransactions(final @PathVariable(name = "userId") Long userId) {
        return this.transactionService.getUserTransactions(userId);
    }

}
