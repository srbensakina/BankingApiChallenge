package com.chanllenge.bankingapi.service;

import com.chanllenge.bankingapi.dto.BalanceResponse;
import com.chanllenge.bankingapi.model.BankAccount;
import com.chanllenge.bankingapi.model.Customer;
import com.chanllenge.bankingapi.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
       MockitoAnnotations.openMocks(this);
    }


         @Test
         void testGetBalances() {
        Long customerId = 1L;

        BankAccount bankAccount1 = BankAccount.builder().id(1L).balance(500F).build();
        BankAccount bankAccount2 = BankAccount.builder().id(2L).balance(700F).build();
        Customer customer = Customer.builder().id(customerId).bankAccounts(Arrays.asList(bankAccount1, bankAccount2)).build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        List<BalanceResponse> balances = customerService.getBalances(customerId);

        assertEquals(2, balances.size());

        BalanceResponse balanceResponse1 = balances.get(0);
        assertEquals(1L, balanceResponse1.getId());
        assertEquals(500F, balanceResponse1.getBalance());

        BalanceResponse balanceResponse2 = balances.get(1);
        assertEquals(2L, balanceResponse2.getId());
        assertEquals(700F, balanceResponse2.getBalance());
    }

    @Test
    void testGetBalancesCustomerNotFound() {

        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerService.getBalances(customerId));
    }
}