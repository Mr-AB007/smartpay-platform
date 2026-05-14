package com.smartpay.account_service.controller;

import com.smartpay.account_service.dto.CreateAccountRequest;
import com.smartpay.account_service.dto.DepositRequest;
import com.smartpay.account_service.dto.WithdrawRequest;
import com.smartpay.account_service.entity.Account;
import com.smartpay.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public Account createAccount(
            @RequestBody CreateAccountRequest request) {

        return accountService.createAccount(request);
    }

    @GetMapping("/{accountNumber}")
    public Account getAccount(
            @PathVariable String accountNumber) {

        return accountService.getAccount(accountNumber);
    }

    @PutMapping("/{accountNumber}/deposit")
    public Account deposit(
            @PathVariable String accountNumber,
            @RequestBody DepositRequest request) {

        return accountService.deposit(
                accountNumber,
                request.getAmount());
    }

    @PutMapping("/{accountNumber}/withdraw")
    public Account withdraw(
            @PathVariable String accountNumber,
            @RequestBody WithdrawRequest request) {

        return accountService.withdraw(
                accountNumber,
                request.getAmount());
    }
}