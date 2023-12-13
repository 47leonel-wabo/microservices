package com.wbt.orderservice.client;

import com.wbt.orderservice.dto.product.ProductResponseDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public record ProductClient(@Value("${product.service.url}") String url) {
    private static WebClient webClient;

    @PostConstruct
    public void getWebClient() {
        webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<ProductResponseDto> getProductById(final String productId) {
        return webClient.get()
                .uri("/{id}", productId)
                .retrieve()
                .bodyToMono(ProductResponseDto.class);
    }
}
