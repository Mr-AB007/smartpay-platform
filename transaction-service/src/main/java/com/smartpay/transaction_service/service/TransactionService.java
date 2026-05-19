package com.smartpay.transaction_service.service;

import com.smartpay.transaction_service.client.AccountServiceClient;
import com.smartpay.transaction_service.dto.AccountResponse;
import com.smartpay.transaction_service.dto.TransferRequest;
import com.smartpay.transaction_service.entity.Transaction;
import com.smartpay.transaction_service.event.TransactionEvent;
import com.smartpay.transaction_service.repository.TransactionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.smartpay.transaction_service.dto.AmountRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;
    private final AccountServiceClient accountClient;

    @Retry(
            name = "accountServiceRetry"
    )
    @CircuitBreaker(
            name = "accountService" ,fallbackMethod = "fallbackTransaction"
    )
    public Transaction createTransaction(TransferRequest request) {
        log.info("Executing createTransaction()");

        log.info("Calling account-service for account validation");

        AccountResponse account = accountClient.getAccount(request.getFromAccount());

        if(account.getBalance()
                .compareTo(request.getAmount()) < 0) {

            throw new RuntimeException(
                    "Insufficient balance"
            );
        }

        AmountRequest amountRequest = new AmountRequest();

        amountRequest.setAmount(request.getAmount());

        //call to account service for withdrawal
        accountClient.withdraw(
                request.getFromAccount(),
                amountRequest
        );

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

    public Transaction fallbackTransaction(
            TransferRequest request,
            Throwable ex) {

        log.error(
                "Transaction failed after retry and circuit breaker activation. account={} amount={} reason={}",
                request.getFromAccount(),
                request.getAmount(),
                ex.getMessage()
        );

        Transaction failedTransaction =
                Transaction.builder()
                        .fromAccount(request.getFromAccount())
                        .toAccount(request.getToAccount())
                        .amount(request.getAmount())
                        .status("FAILED")
                        .createdAt(LocalDateTime.now())
                        .build();

        return transactionRepository.save(
                failedTransaction
        );
    }
}