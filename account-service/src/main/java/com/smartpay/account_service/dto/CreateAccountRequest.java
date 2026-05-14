package com.smartpay.account_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {

    private String holderName;

    private BigDecimal initialBalance;
}