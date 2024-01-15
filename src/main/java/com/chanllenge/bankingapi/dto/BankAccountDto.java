package com.chanllenge.bankingapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BankAccountDto {

    private Long id;
    private Float balance;
    private Long customerId;
}
