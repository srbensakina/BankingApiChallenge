package com.chanllenge.bankingapi.repository;

import com.chanllenge.bankingapi.model.BankAccount;
import com.chanllenge.bankingapi.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("SELECT b.balance FROM BankAccount b WHERE b.customer.id = :customerId")
    List<Float> findBalancesByCustomerId(@Param("customerId") Long customerId);
}
