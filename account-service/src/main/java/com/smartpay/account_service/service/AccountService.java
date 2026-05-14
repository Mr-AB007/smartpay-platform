package com.smartpay.account_service.service;

import com.smartpay.account_service.dto.CreateAccountRequest;
import com.smartpay.account_service.entity.Account;
import com.smartpay.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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

    public Account getAccount(String accountNumber) {

        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));
    }

    public Account deposit(String accountNumber, BigDecimal amount) {

        Account account = getAccount(accountNumber);

        account.setBalance(account.getBalance().add(amount));

        return accountRepository.save(account);
    }

    public Account withdraw(String accountNumber, BigDecimal amount) {

        Account account = getAccount(accountNumber);

        if(account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        return accountRepository.save(account);
    }

    private String generateAccountNumber() {

        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10);
    }
}