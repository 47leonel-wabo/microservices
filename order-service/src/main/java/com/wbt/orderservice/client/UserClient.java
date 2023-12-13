package com.wbt.orderservice.client;

import com.wbt.orderservice.dto.user.UserDto;
import com.wbt.orderservice.dto.user.UserTransactionRequestDto;
import com.wbt.orderservice.dto.user.UserTransactionResponseDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public record UserClient(@Value("${user.service.url}") String url) {

    private static WebClient webClient;

    @PostConstruct
    public WebClient getWebClient() {
        return webClient = WebClient.builder().baseUrl(url).build();
    }

    public Mono<UserDto> getUserById(final Long userId) {
        return webClient.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    public Mono<UserTransactionResponseDto> performTransaction(final Mono<UserTransactionRequestDto> requestDtoMono) {
        return webClient.post()
                .uri("/transactions")
                .body(requestDtoMono, UserTransactionRequestDto.class)
                .retrieve()
                .bodyToMono(UserTransactionResponseDto.class);
    }
}
