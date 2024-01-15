package com.chanllenge.bankingapi.data;

import com.chanllenge.bankingapi.model.Customer;
import com.chanllenge.bankingapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {


        private final CustomerRepository customerRepository;


        @Override
        public void run(String... args) throws Exception {
            List<Customer> customers = Arrays.asList(
                    new Customer(1L, "Arisha Barron",new ArrayList<>()),
                    new Customer(2L, "Branden Gibson",new ArrayList<>()),
                    new Customer(3L, "Rhonda Church",new ArrayList<>()),
                    new Customer(4L, "Georgina Hazel",new ArrayList<>())
            );

            // Save customers to the database
            customerRepository.saveAll(customers);

            System.out.println("Data loaded successfully.");
        }
    }

