package com.chanllenge.bankingapi.service;

import com.chanllenge.bankingapi.dto.BalanceResponse;
import com.chanllenge.bankingapi.dto.CustomerDto;
import com.chanllenge.bankingapi.model.Customer;
import com.chanllenge.bankingapi.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    public List<BalanceResponse> getBalances(Long customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> customer.getBankAccounts().stream()
                        .map(bankAccount -> new BalanceResponse(bankAccount.getId(),bankAccount.getBalance()))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
    }

    public List<CustomerDto> getAllCustomers(){


       List<Customer> customers =  (List<Customer>) customerRepository.findAll();

      return customers.stream().map(customer -> new CustomerDto(customer.getId(), customer.getName())).collect(Collectors.toList());

    }

}
