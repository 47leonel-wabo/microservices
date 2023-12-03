package com.wbt.productservice.util;

import com.wbt.productservice.dto.ProductRequestDto;
import com.wbt.productservice.dto.ProductResponseDto;
import com.wbt.productservice.entity.Product;

public record EntityToDto() {
    public static ProductResponseDto toProductResponseDto(final Product product) {
        return new ProductResponseDto(product.id(), product.name(), product.price());
    }

    public static Product toProductEntity(final ProductRequestDto requestDto) {
        return new Product(null, requestDto.name(), requestDto.price());
    }
}
