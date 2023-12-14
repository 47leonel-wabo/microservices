package com.wbt.productservice.util;

import com.wbt.productservice.dto.ProductRequestDto;
import com.wbt.productservice.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Component
public record CreateFakeProducts(ProductService productService) {
    @Bean
    public CommandLineRunner runner() {
        return args -> {
            final var p1 = new ProductRequestDto("4k-TV", 1000.0);
            final var p2 = new ProductRequestDto("Headphone", 300.0);

            Flux.just(p1, p2)
                    .concatWith(newProductInsertWithDelay())
                    .flatMap(requestDto -> this.productService.addProduct(Mono.just(requestDto)))
                    .subscribe(System.out::println);
        };
    }

    public Flux<ProductRequestDto> newProductInsertWithDelay() {
        return Flux.range(1, 1000)
                .delayElements(Duration.ofSeconds(2))
                .map(i -> new ProductRequestDto("Product %s".formatted(i), ThreadLocalRandom.current().nextDouble()));
    }
}
