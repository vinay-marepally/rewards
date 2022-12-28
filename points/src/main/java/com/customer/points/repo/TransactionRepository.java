package com.customer.points.repo;

import com.customer.points.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,Long> {
    List<Transactions> findAllByCustomerId(Long customerId);

    @Query("select customerId from Transactions")
    Set<Long> findAllCustomerIds();
}
