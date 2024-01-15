package com.chanllenge.bankingapi.controller;

import com.chanllenge.bankingapi.dto.BalanceResponse;
import com.chanllenge.bankingapi.service.CustomerService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testGetBalances_Success() {
        // Arrange
        Long customerId = 1L;

        List<BalanceResponse> balances = Arrays.asList(
                new BalanceResponse(1L, 500F),
                new BalanceResponse(2L, 1000F)
        );

        when(customerService.getBalances(customerId)).thenReturn(balances);

        ResponseEntity<?> response = customerController.getBalances(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(balances, response.getBody());
        verify(customerService, times(1)).getBalances(customerId);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void testGetBalances_NotFound() {
        Long customerId = 1L;

        when(customerService.getBalances(customerId)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = customerController.getBalances(customerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(customerService, times(1)).getBalances(customerId);
        verifyNoMoreInteractions(customerService);
    }
}