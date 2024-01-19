package com.chanllenge.bankingapi.service;

import com.chanllenge.bankingapi.dto.TransferHistoryResponse;
import com.chanllenge.bankingapi.model.BankAccount;
import com.chanllenge.bankingapi.model.TransferHistory;
import com.chanllenge.bankingapi.repository.TransferHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransferHistoryServiceTest {

    @Mock
    private TransferHistoryRepository transferHistoryRepository;

    @InjectMocks
    private TransferHistoryService transferHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getTransferHistory() {

        Long accountId = 1L;

        BankAccount sourceAccount1 = BankAccount.builder().id(10L).build();
        BankAccount destinationAccount1 = BankAccount.builder().id(20L).build();

        BankAccount sourceAccount2 = BankAccount.builder().id(30L).build();
        BankAccount destinationAccount2 = BankAccount.builder().id(40L).build();

        TransferHistory transferHistory1 = TransferHistory.builder().id(1L).sourceAccount(sourceAccount1).destinationAccount(destinationAccount1).amount(200F).build();
        TransferHistory transferHistory2 = TransferHistory.builder().id(2L).sourceAccount(sourceAccount2).destinationAccount(destinationAccount2).amount(300F).build();

        when(transferHistoryRepository.findAllBySourceAccount_IdOrDestinationAccount_Id(accountId, accountId))
                .thenReturn(Arrays.asList(transferHistory1, transferHistory2));

        List<TransferHistoryResponse> transferHistoryList = transferHistoryService.getTransferHistory(accountId);

        assertEquals(2, transferHistoryList.size());

        TransferHistoryResponse transferHistoryResponse1 = transferHistoryList.get(0);
        assertEquals(10L, transferHistoryResponse1.getSource());
        assertEquals(20L, transferHistoryResponse1.getDestination());
        assertEquals(200F, transferHistoryResponse1.getAmountTransferred());

        TransferHistoryResponse transferHistoryResponse2 = transferHistoryList.get(1);
        assertEquals(30L, transferHistoryResponse2.getSource());
        assertEquals(40L, transferHistoryResponse2.getDestination());
        assertEquals(300F, transferHistoryResponse2.getAmountTransferred());
    }
    @Test
    void testGetTransferHistoryEmpty() {
        Long accountId = 1L;

        when(transferHistoryRepository.findAllBySourceAccount_IdOrDestinationAccount_Id(accountId, accountId)).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> transferHistoryService.getTransferHistory(accountId));
    }
    }
