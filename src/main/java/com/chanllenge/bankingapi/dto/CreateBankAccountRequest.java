package com.chanllenge.bankingapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBankAccountRequest {

   private Long customerId;
   private Float initialDeposit ;
}
