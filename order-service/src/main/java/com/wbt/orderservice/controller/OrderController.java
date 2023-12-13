package com.wbt.orderservice.controller;

import com.wbt.orderservice.dto.OrderRequestDto;
import com.wbt.orderservice.dto.OrderResponseDto;
import com.wbt.orderservice.service.OrderFulfillmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/orders"})
public record OrderController(OrderFulfillmentService orderService) {

    @PostMapping
    public Mono<ResponseEntity<OrderResponseDto>> processOrder(final @RequestBody Mono<OrderRequestDto> orderRequestDtoMono) {
        return this.orderService.processOrder(orderRequestDtoMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping(path = {"/users/{id}"})
    public Flux<OrderResponseDto> getUserOrders(final @PathVariable(name = "id") Long id) {
        return this.orderService.findUserOrders(id);
    }
}
