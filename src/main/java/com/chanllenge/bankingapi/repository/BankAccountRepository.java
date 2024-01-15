package com.chanllenge.bankingapi.repository;

import com.chanllenge.bankingapi.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long > {


}
