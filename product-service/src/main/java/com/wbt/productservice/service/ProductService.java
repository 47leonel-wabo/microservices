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

@Service
public record ProductService(ProductRepository productRepository) {

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

    public Mono<ProductResponseDto> addProduct(final Mono<ProductRequestDto> productRequestDto) {
        return productRequestDto
                .flatMap(requestDto -> this.productRepository.save(EntityToDto.toProductEntity(requestDto)))
                .map(EntityToDto::toProductResponseDto);
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
