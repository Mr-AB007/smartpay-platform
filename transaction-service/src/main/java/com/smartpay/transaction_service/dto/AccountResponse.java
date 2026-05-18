package com.smartpay.transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private String holderName;
    private BigDecimal balance;
}