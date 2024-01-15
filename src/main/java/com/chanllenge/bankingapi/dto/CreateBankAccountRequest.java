package com.chanllenge.bankingapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBankAccountRequest {

   @NotNull(message = "Customer Id deposit is mandatory")
   private Long customerId;

   @NotNull(message = "Initial deposit is mandatory")
   private Float initialDeposit ;
}
