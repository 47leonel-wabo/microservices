package com.wbt.productservice.controller;

import com.wbt.productservice.dto.ProductResponseDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = {"/stream/products"})
public record SSEController(Flux<ProductResponseDto> flux) {
    @GetMapping(path = {"/{max-price}"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductResponseDto> sinkOnProduct(final @PathVariable(name = "max-price") Double maxPrice) {
        return this.flux.filter(dto -> dto.price() <= maxPrice);
    }

}
