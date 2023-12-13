package com.wbt.orderservice.service;

import com.wbt.orderservice.client.ProductClient;
import com.wbt.orderservice.client.UserClient;
import com.wbt.orderservice.dto.OrderRequestDto;
import com.wbt.orderservice.dto.OrderResponseDto;
import com.wbt.orderservice.dto.RequestContext;
import com.wbt.orderservice.dto.user.TransactionStatus;
import com.wbt.orderservice.dto.user.UserTransactionRequestDto;
import com.wbt.orderservice.entity.PurchaseOrder;
import com.wbt.orderservice.repository.OrderRepository;
import com.wbt.orderservice.util.OrderStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public record OrderFulfillmentService(ProductClient productClient, UserClient userClient,
                                      OrderRepository orderRepository) {

    public Mono<OrderResponseDto> processOrder(final Mono<OrderRequestDto> orderRequestDtoMono) {
        // STEP 1: Get user
        // STEP 2: Perform transaction
        // STEP 3: Persist order action
        // STEP 4: Return response
        return orderRequestDtoMono.map(RequestContext::new)
                .flatMap(this::callProductService)
                .doOnNext(this::setTransactionRequestDto)
                .flatMap(this::callUserServiceForTransaction)
                .map(this::definePurchaseOrder)
                .map(orderRepository::save) // blocking operation
                .map(purchaseOrder -> new OrderResponseDto(
                        purchaseOrder.getId(),
                        purchaseOrder.getUserId(),
                        purchaseOrder.getProductId(),
                        purchaseOrder.getAmount(),
                        purchaseOrder.getStatus()))
                .subscribeOn(Schedulers.boundedElastic()); // because database access is blocking - JPA effect
    }

    private Mono<RequestContext> callProductService(final RequestContext context) {
        return this.productClient.getProductById(context.orderRequestDto().productId())
                .doOnNext(context::setProductResponseDto)
                // RESILIENT - If any issue contacting the product service, retry 5 times each 1s
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(context);
    }

    private Mono<RequestContext> callUserServiceForTransaction(final RequestContext context) {
        return this.userClient
                .performTransaction(Mono.fromSupplier(context::getTransactionRequestDto))
                .doOnNext(context::setTransactionResponseDto)
                .thenReturn(context);
    }

    private void setTransactionRequestDto(final RequestContext context) {
        final var transactionRequestDto = new UserTransactionRequestDto(
                context.orderRequestDto().userId(), context.getProductResponseDto().price());
        context.setTransactionRequestDto(transactionRequestDto);
    }

    private PurchaseOrder definePurchaseOrder(final RequestContext context) {
        return new PurchaseOrder(
                context.getProductResponseDto().id(),
                context.getTransactionResponseDto().userId(),
                context.getTransactionResponseDto().amount(),
                context.getTransactionResponseDto().status().equals(TransactionStatus.APPROVED) ? OrderStatus.COMPLETED : OrderStatus.FAILED
        );
    }

    public Flux<OrderResponseDto> findUserOrders(final Long userId) {
        // calling to the database via jpa is a blocking operation, so must be done into pipeline
        return Flux.fromStream(this.orderRepository.findByUserId(userId).stream())
                .map(purchaseOrder -> new OrderResponseDto(
                        purchaseOrder.getId(),
                        purchaseOrder.getUserId(),
                        purchaseOrder.getProductId(),
                        purchaseOrder.getAmount(),
                        purchaseOrder.getStatus()))
                .subscribeOn(Schedulers.boundedElastic()); // (ESSENTIAL) reactively retrieve data
    }

}
