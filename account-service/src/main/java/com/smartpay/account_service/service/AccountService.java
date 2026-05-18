package com.smartpay.account_service.service;

import com.smartpay.account_service.dto.CreateAccountRequest;
import com.smartpay.account_service.entity.Account;
import com.smartpay.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(
            CreateAccountRequest request) {

        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .holderName(request.getHolderName())
                .balance(request.getInitialBalance())
                .build();

        return accountRepository.save(account);
    }

    @Cacheable(
            value = "accounts",
            key = "#accountNumber"
    )
    public Account getAccount(String accountNumber) {

        log.info("Fetching account details for accountNumber={}", accountNumber);

        return accountRepository.findByAccountNumber(
                accountNumber
        ).orElseThrow(() ->
                new RuntimeException("Account not found"));
    }

    @CacheEvict(
            value = "accounts",
            key = "#accountNumber"
    )
    public Account deposit(String accountNumber, BigDecimal amount) {

        log.info("Deposit request received for accountNumber={} amount={}", accountNumber, amount
        );

        Account account = getAccount(accountNumber);

        account.setBalance(account.getBalance().add(amount));

        Account savedAccount = accountRepository.save(account);

        log.info("Deposit successful for accountNumber={} amount={} updatedBalance={}", accountNumber, amount, savedAccount.getBalance());

        return savedAccount;
    }

    @CacheEvict(
            value = "accounts",
            key = "#accountNumber"
    )
    public Account withdraw(String accountNumber, BigDecimal amount) {

        log.info("Withdrawal request received for accountNumber={} amount={}", accountNumber, amount);

        Account account = getAccount(accountNumber);

        if(account.getBalance().compareTo(amount) < 0) {

            log.error("Withdrawal failed due to insufficient balance for accountNumber={} requestedAmount={} availableBalance={}", accountNumber, amount, account.getBalance());

            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        Account savedAccount = accountRepository.save(account);

        log.info("Withdrawal successful for accountNumber={} amount={} updatedBalance={}", accountNumber, amount, savedAccount.getBalance()
        );

        return savedAccount;
    }

    private String generateAccountNumber() {

        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10);
    }
}