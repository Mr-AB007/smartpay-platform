package com.smartpay.transaction_service.event;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEvent {

    private String transactionId;

    private String fromAccount;

    private String toAccount;

    private BigDecimal amount;

    private String status;
}