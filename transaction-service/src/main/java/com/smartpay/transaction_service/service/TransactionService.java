package com.smartpay.transaction_service.service;

import com.smartpay.transaction_service.dto.TransferRequest;
import com.smartpay.transaction_service.entity.Transaction;
import com.smartpay.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction createTransaction(
            TransferRequest request) {

        Transaction transaction = Transaction.builder()
                .fromAccount(request.getFromAccount())
                .toAccount(request.getToAccount())
                .amount(request.getAmount())
                .status("SUCCESS")
                .createdAt(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }
}