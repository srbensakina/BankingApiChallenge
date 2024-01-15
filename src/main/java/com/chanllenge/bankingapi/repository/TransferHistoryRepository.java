package com.chanllenge.bankingapi.repository;


import com.chanllenge.bankingapi.model.TransferHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferHistoryRepository extends CrudRepository<TransferHistory, Long  > {

    List<TransferHistory> findAllBySourceAccount_IdOrDestinationAccount_Id(Long sourceAccountId, Long destinationAccountId);

}
