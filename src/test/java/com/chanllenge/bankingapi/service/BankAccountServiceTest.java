package com.chanllenge.bankingapi.service;

import com.chanllenge.bankingapi.dto.CreateBankAccountRequest;
import com.chanllenge.bankingapi.model.BankAccount;
import com.chanllenge.bankingapi.model.Customer;
import com.chanllenge.bankingapi.model.TransferHistory;
import com.chanllenge.bankingapi.repository.BankAccountRepository;
import com.chanllenge.bankingapi.repository.CustomerRepository;
import com.chanllenge.bankingapi.repository.TransferHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransferHistoryRepository transferHistoryRepository;
    @InjectMocks
    private BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
       MockitoAnnotations.openMocks(this) ;
    }
    @Test
    void testCreateBankAccount() {
        Customer customer = Customer.builder().id(1L).bankAccounts(new ArrayList<>()).build();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        when(bankAccountRepository.save(any(BankAccount.class))).thenAnswer(invocation -> {
            BankAccount bankAccountArgument = invocation.getArgument(0);
            return bankAccountArgument;
        });

        CreateBankAccountRequest request = CreateBankAccountRequest.builder().customerId(1L).initialDeposit(200F).build();

        BankAccount createdAccount = bankAccountService.createBankAccount(request);

        assertNotNull(createdAccount);
        assertEquals(1L, createdAccount.getCustomer().getId());

    }



    @Test
    void testTransferAmounts() {

            Long transferredTo = 1L;
            Long transferredFrom = 2L;
            Float amountTransferred = 100F;

            BankAccount bankAccountTo = BankAccount.builder().id(transferredTo).balance(500F).build();
            BankAccount bankAccountFrom = BankAccount.builder().id(transferredFrom).balance(300F).build();

            when(bankAccountRepository.findById(transferredTo)).thenReturn(Optional.of(bankAccountTo));
            when(bankAccountRepository.findById(transferredFrom)).thenReturn(Optional.of(bankAccountFrom));

            when(bankAccountRepository.save(any(BankAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

            when(transferHistoryRepository.save(any(TransferHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

           bankAccountService.transferAmounts(amountTransferred, transferredTo, transferredFrom);

            assertEquals(200F, bankAccountFrom.getBalance());
            assertEquals(600F, bankAccountTo.getBalance());

            TransferHistory transferHistory = TransferHistory.builder()
                    .amount(amountTransferred)
                    .destinationAccount(bankAccountTo)
                    .sourceAccount(bankAccountFrom)
                    .build();

            assertEquals(transferHistory, transferHistoryRepository.save(transferHistory));
        }


    @Test
    void testCreateBankAccountCustomerNotFound() {
        CreateBankAccountRequest request = CreateBankAccountRequest.builder()
                .customerId(1L)
                .initialDeposit(200F)
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            bankAccountService.createBankAccount(request);
        });

        verify(customerRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(customerRepository, bankAccountRepository);
    }

    @Test
    void testTransferAmountsSameAccount() {
        Long transferredTo = 1L;
        Long transferredFrom = 1L;
        Float amountTransferred = 100F;

        BankAccount bankAccountTo = BankAccount.builder().id(transferredTo).balance(500F).build();

        when(bankAccountRepository.findById(transferredTo)).thenReturn(Optional.of(bankAccountTo));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bankAccountService.transferAmounts(amountTransferred, transferredTo, transferredFrom));

        verify(bankAccountRepository, times(2)).findById(any(Long.class));
        verifyNoMoreInteractions(bankAccountRepository, transferHistoryRepository);

        assertEquals("Cannot transfer money to the same account", exception.getMessage());
    }

    @Test
    void testTransferAmountsAccountNotFound() {
        Long transferredTo = 1L;
        Long transferredFrom = 2L;
        Float amountTransferred = 100F;

        when(bankAccountRepository.findById(transferredTo)).thenReturn(Optional.empty());
        when(bankAccountRepository.findById(transferredFrom)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bankAccountService.transferAmounts(amountTransferred, transferredTo, transferredFrom);
        });

        verify(bankAccountRepository, times(2)).findById(any(Long.class));
        verifyNoMoreInteractions(bankAccountRepository, transferHistoryRepository);

        assertEquals("Bank Account not found", exception.getMessage());
    }

}