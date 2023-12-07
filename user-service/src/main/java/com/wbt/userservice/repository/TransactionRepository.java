package com.wbt.userservice.repository;

import com.wbt.userservice.entity.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {

    @Query("select * from user_transaction where user_id = :userId")
    Flux<Transaction> fetchUserTransactions(final Long userId);

}
