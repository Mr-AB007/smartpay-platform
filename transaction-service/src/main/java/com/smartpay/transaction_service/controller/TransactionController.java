package com.smartpay.transaction_service.controller;

import com.smartpay.transaction_service.dto.TransferRequest;
import com.smartpay.transaction_service.entity.Transaction;
import com.smartpay.transaction_service.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Transaction APIs",
        description = "Money transfer operations"
)
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(
            summary = "Transfer Money",
            description = "Transfers funds between accounts"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transfer successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Insufficient balance"
            ),
            @ApiResponse(
                    responseCode = "503",
                    description = "Account service unavailable"
            )
    })

    @PostMapping("/transfer")
    public Transaction transfer(
            @Valid @RequestBody TransferRequest request) {

        return transactionService.createTransaction(request);
    }
}