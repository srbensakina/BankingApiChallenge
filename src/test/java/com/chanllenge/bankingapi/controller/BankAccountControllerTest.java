package com.chanllenge.bankingapi.controller;

import com.chanllenge.bankingapi.dto.BankAccountDto;
import com.chanllenge.bankingapi.dto.CreateBankAccountRequest;
import com.chanllenge.bankingapi.dto.TransferHistoryResponse;
import com.chanllenge.bankingapi.model.BankAccount;
import com.chanllenge.bankingapi.service.BankAccountService;
import com.chanllenge.bankingapi.service.TransferHistoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BankAccountControllerTest {

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private TransferHistoryService transferHistoryService;

    @InjectMocks
    private BankAccountController bankAccountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBankAccount_Success() {
        when(bankAccountService.createBankAccount(any(CreateBankAccountRequest.class)))
                .thenReturn(new BankAccount());

        ResponseEntity<?> response = bankAccountController.createBankAccount(CreateBankAccountRequest.builder().build());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(BankAccount.class, response.getBody().getClass());
    }

    @Test
    void testCreateBankAccount_Failure() {
        when(bankAccountService.createBankAccount(any(CreateBankAccountRequest.class)))
                .thenThrow(new IllegalArgumentException("Invalid input"));

        ResponseEntity<?> response = bankAccountController.createBankAccount(CreateBankAccountRequest.builder().build());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input", response.getBody());
    }

    @Test
    void testGetAllBankAccounts() {
        when(bankAccountService.getAllBankAccounts())
                .thenReturn(Collections.singletonList(new BankAccountDto()));

        ResponseEntity<List<BankAccountDto>> response = bankAccountController.getAllBankAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }


    @Test
    void testTransferAmounts() {
        Float amountTransferred = 100F;
        Long transferredTo = 1L;
        Long transferredFrom = 2L;

        ResponseEntity<?> response = bankAccountController.transferAmounts(amountTransferred, transferredTo, transferredFrom);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Amount Transferred successfully", response.getBody());
        verify(bankAccountService, times(1)).transferAmounts(amountTransferred, transferredTo, transferredFrom);
        verifyNoMoreInteractions(bankAccountService);
    }


    @Test
    void testGetTransferHistory() {
        Long accountId = 1L;

        List<TransferHistoryResponse> transferHistoryList = Arrays.asList(
                new TransferHistoryResponse(1L, 2L, 100F),
                new TransferHistoryResponse(3L, 1L, 50F)
        );

        when(transferHistoryService.getTransferHistory(accountId)).thenReturn(transferHistoryList);

        ResponseEntity<?> response = bankAccountController.getTransferHistory(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferHistoryList, response.getBody());
        verify(transferHistoryService, times(1)).getTransferHistory(accountId);
        verifyNoMoreInteractions(transferHistoryService);
    }

}