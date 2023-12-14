package com.wbt.productservice.config;

import com.wbt.productservice.dto.ProductResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {
    @Bean
    public Sinks.Many<ProductResponseDto> sink() {
        return Sinks.many().replay().limit(1);
    }

    @Bean
    public Flux<ProductResponseDto> productBroadcast(final Sinks.Many<ProductResponseDto> sink) {
        return sink.asFlux();
    }
}
