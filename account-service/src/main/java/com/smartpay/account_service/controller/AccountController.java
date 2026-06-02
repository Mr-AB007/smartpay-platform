package com.smartpay.account_service.controller;

import com.smartpay.account_service.dto.CreateAccountRequest;
import com.smartpay.account_service.dto.DepositRequest;
import com.smartpay.account_service.dto.WithdrawRequest;
import com.smartpay.account_service.entity.Account;
import com.smartpay.account_service.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(
        name = "Account APIs",
        description = "Operations related to bank accounts"
)
public class AccountController {

    private final AccountService accountService;

    @Operation(
            summary = "Create Account",
            description = "Creates a new SmartPay account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })

    @PostMapping
    public Account createAccount(
            @RequestBody CreateAccountRequest request) {

        return accountService.createAccount(request);
    }

    @Operation(
            summary = "Account Detail",
            description = "Get Account details by Account Number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account details found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @GetMapping("/{accountNumber}")
    public Account getAccount(
            @PathVariable String accountNumber) {

        return accountService.getAccount(accountNumber);
    }

    @Operation(
            summary = "Deposit Amount",
            description = "Deposit amount into account"
    )

    @PutMapping("/{accountNumber}/deposit")
    public Account deposit(
            @PathVariable String accountNumber,
            @RequestBody DepositRequest request) {

        return accountService.deposit(
                accountNumber,
                request.getAmount());
    }
    @Operation(
            summary = "Update Account",
            description = "Withdraw amount from account"
    )

    @PutMapping("/{accountNumber}/withdraw")
    public Account withdraw(
            @PathVariable String accountNumber,
            @RequestBody WithdrawRequest request) {

        return accountService.withdraw(
                accountNumber,
                request.getAmount());
    }
}