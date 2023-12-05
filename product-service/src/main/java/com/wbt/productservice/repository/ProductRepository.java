package com.wbt.productservice.repository;

import com.wbt.productservice.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findByPriceBetween(final Double min, final Double max);

    Flux<Product> findByPriceBetween(final Range<Double> range);
}
