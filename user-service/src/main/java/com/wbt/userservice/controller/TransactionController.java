package com.wbt.userservice.controller;

import com.wbt.userservice.dto.UserTransactionRequestDto;
import com.wbt.userservice.dto.UserTransactionResponseDto;
import com.wbt.userservice.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/transactions"})
public record TransactionController(TransactionService transactionService) {
    @PostMapping
    Mono<UserTransactionResponseDto> perform(final @RequestBody Mono<UserTransactionRequestDto> requestDtoMono) {
        return this.transactionService().create(requestDtoMono);
    }

}
