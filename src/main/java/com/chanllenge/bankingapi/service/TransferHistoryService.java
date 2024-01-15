package com.chanllenge.bankingapi.service;


import com.chanllenge.bankingapi.dto.TransferHistoryResponse;
import com.chanllenge.bankingapi.repository.TransferHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferHistoryService {
    private final TransferHistoryRepository transferHistoryRepository;

    public List<TransferHistoryResponse> getTransferHistory(Long accountId) {
        List<TransferHistoryResponse> transferHistoryList = transferHistoryRepository
                .findAllBySourceAccount_IdOrDestinationAccount_Id(accountId, accountId).stream()
                .map(transferHistory -> new TransferHistoryResponse(transferHistory.getSourceAccount().getId(),transferHistory.getDestinationAccount().getId(), transferHistory.getAmount()))
                .collect(Collectors.toList());

        if (transferHistoryList.isEmpty()) {
            throw new EntityNotFoundException("Transfer history not found for account ID: " + accountId);
        }

        return transferHistoryList;
    }
}
