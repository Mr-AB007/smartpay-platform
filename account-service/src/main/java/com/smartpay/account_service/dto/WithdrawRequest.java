package com.smartpay.account_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequest {

    private BigDecimal amount;
}