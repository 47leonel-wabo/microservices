package com.wbt.orderservice.controller;

import com.wbt.orderservice.dto.OrderRequestDto;
import com.wbt.orderservice.dto.OrderResponseDto;
import com.wbt.orderservice.service.OrderFulfillmentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/orders"})
public record OrderController(OrderFulfillmentService orderService) {

    @PostMapping
    public Mono<OrderResponseDto> processOrder(final @RequestBody Mono<OrderRequestDto> orderRequestDtoMono) {
        return this.orderService.processOrder(orderRequestDtoMono);
    }

    @GetMapping(path = {"/users/{id}"})
    public Flux<OrderResponseDto> getUserOrders(final @PathVariable(name = "id") Long id) {
        return this.orderService.findUserOrders(id);
    }
}
