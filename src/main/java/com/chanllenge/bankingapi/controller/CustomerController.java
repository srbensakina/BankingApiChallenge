package com.chanllenge.bankingapi.controller;

import com.chanllenge.bankingapi.dto.BalanceResponse;
import com.chanllenge.bankingapi.dto.CustomerDto;
import com.chanllenge.bankingapi.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}/balances")
    public ResponseEntity<?> getBalances(@PathVariable Long customerId) {
        try {
            List<BalanceResponse> balances = customerService.getBalances(customerId);
            return balances.isEmpty()
                    ? ResponseEntity.notFound().build()
                    : ResponseEntity.ok(balances);
        } catch (EntityNotFoundException ex) {
          return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("")
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<CustomerDto> customers = customerService.getAllCustomers();
            return customers.isEmpty()
                    ? ResponseEntity.notFound().build()
                    : ResponseEntity.ok(customers);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




}

