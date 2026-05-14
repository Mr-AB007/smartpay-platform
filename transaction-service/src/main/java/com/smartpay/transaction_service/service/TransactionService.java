package com.smartpay.transaction_service.service;

import com.smartpay.transaction_service.dto.TransferRequest;
import com.smartpay.transaction_service.entity.Transaction;
import com.smartpay.transaction_service.event.TransactionEvent;
import com.smartpay.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public Transaction createTransaction(
            TransferRequest request) {

        Transaction transaction = Transaction.builder()
                .fromAccount(request.getFromAccount())
                .toAccount(request.getToAccount())
                .amount(request.getAmount())
                .status("SUCCESS")
                .createdAt(LocalDateTime.now())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        TransactionEvent event = TransactionEvent.builder()
                .transactionId(savedTransaction.getId().toString())
                .fromAccount(savedTransaction.getFromAccount())
                .toAccount(savedTransaction.getToAccount())
                .amount(request.getAmount())
                .status("SUCCESS")
                .build();

        kafkaTemplate.send(
                "transaction-events",
                UUID.randomUUID().toString(),
                event
        );

        return savedTransaction;
    }
}