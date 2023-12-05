package com.wbt.productservice.controller;

import com.wbt.productservice.dto.ProductRequestDto;
import com.wbt.productservice.dto.ProductResponseDto;
import com.wbt.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/products"})
public record ProductController(ProductService productService) {
    @GetMapping
    public Flux<ProductResponseDto> getAll() {
        return this.productService.getAllProducts();
    }

    @GetMapping(path = {"/{id}"})
    public Mono<ResponseEntity<ProductResponseDto>> getById(final @PathVariable(name = "id") String id) {
        return this.productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path = {"/price"})
    public Flux<ProductResponseDto> getByPriceRange(final @RequestParam("max") Double max,
                                                    final @RequestParam("min") Double min) {
        return this.productService.getByPriceRange(min, max);
    }

    @PostMapping
    public Mono<ProductResponseDto> save(final @RequestBody Mono<ProductRequestDto> requestDto) {
        return this.productService.addProduct(requestDto);
    }

    @PutMapping(path = {"/{id}"})
    public Mono<ResponseEntity<ProductResponseDto>> update(final @PathVariable(name = "id") String id, final @RequestBody Mono<ProductRequestDto> requestDtoMono) {
        return this.productService.updateProduct(id, requestDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping(path = {"/{id}"})
    public Mono<Void> delete(final @PathVariable(name = "id") String id) {
        return this.productService.deleteProductById(id);
    }

}
