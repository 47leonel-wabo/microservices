package com.wbt.productservice.service;

import com.wbt.productservice.dto.ProductRequestDto;
import com.wbt.productservice.dto.ProductResponseDto;
import com.wbt.productservice.entity.Product;
import com.wbt.productservice.repository.ProductRepository;
import com.wbt.productservice.util.EntityToDto;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public record ProductService(
        ProductRepository productRepository,
        Sinks.Many<ProductResponseDto> productSink) {

    public Flux<ProductResponseDto> getAllProducts() {
        return this.productRepository
                .findAll()
                .map(EntityToDto::toProductResponseDto);
    }

    public Mono<ProductResponseDto> getProductById(final String productId) {
        return this.productRepository
                .findById(productId)
                .map(EntityToDto::toProductResponseDto);
    }

    /**
     * @param productRequestDto this DTO represents craft of object to be persisted
     * @return Mono of ProductResponseDto which represents a craft of saved object
     * This method also emit an event to notify the frontend UI about the newly saved product
     */
    public Mono<ProductResponseDto> addProduct(final Mono<ProductRequestDto> productRequestDto) {
        return productRequestDto
                .flatMap(requestDto -> this.productRepository.save(EntityToDto.toProductEntity(requestDto)))
                .map(EntityToDto::toProductResponseDto)
                .doOnNext(this.productSink::tryEmitNext); // Server Sent Event to the frontend
    }

    public Mono<ProductResponseDto> updateProduct(final String productId, final Mono<ProductRequestDto> requestDto) {
        return requestDto.flatMap(
                dto -> productRepository.findById(productId)
                        .map(product -> new Product(productId, dto.name(), dto.price()))
                        .flatMap(productRepository::save)
                        .map(EntityToDto::toProductResponseDto));
    }

    public Mono<Void> deleteProductById(final String id) {
        return this.productRepository.findById(id)
                .flatMap(this.productRepository::delete);
    }

    public Flux<ProductResponseDto> getByPriceRange(final Double minPrice, final Double maxPrice) {
        return this.productRepository
                .findByPriceBetween(Range.closed(minPrice, maxPrice))
                .map(EntityToDto::toProductResponseDto);
    }
}
