package com.chanllenge.bankingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class TransferHistoryResponse {

    private Long source;
    private Long destination;
    private Float amountTransferred;

}
