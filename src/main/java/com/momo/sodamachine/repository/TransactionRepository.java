package com.momo.sodamachine.repository;

import com.momo.sodamachine.model.Product;
import com.momo.sodamachine.model.Transaction;
import com.momo.sodamachine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT COUNT(t) FROM Transaction t " +
            "WHERE t.user = :user " +
            "AND t.product = :product " +
            "AND t.status = 'COMPLETED' " +
            "AND DATE(t.transactionTime) = CURRENT_DATE")
    Integer checkConsecutivePurchases(@Param("user") User user, @Param("product") Product product);
}
