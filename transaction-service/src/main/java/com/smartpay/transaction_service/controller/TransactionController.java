package com.smartpay.transaction_service.controller;

import com.smartpay.transaction_service.dto.TransferRequest;
import com.smartpay.transaction_service.entity.Transaction;
import com.smartpay.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public Transaction transfer(
            @RequestBody TransferRequest request) {

        return transactionService.createTransaction(request);
    }
}