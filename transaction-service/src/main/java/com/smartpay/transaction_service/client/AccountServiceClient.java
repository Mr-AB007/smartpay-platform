package com.smartpay.transaction_service.client;

import com.smartpay.transaction_service.dto.AccountResponse;
import com.smartpay.transaction_service.dto.AmountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service", url = "${account.service.url}")
public interface AccountServiceClient {


    @GetMapping("/api/accounts/{accountNumber}")
    public AccountResponse getAccount(@PathVariable String accountNumber);

    @PutMapping("/api/accounts/{accountNumber}/withdraw")
    AccountResponse withdraw(
            @PathVariable String accountNumber,
            @RequestBody AmountRequest request
    );
}
