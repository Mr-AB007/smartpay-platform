package com.smartpay.account_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    @NotBlank(
            message = "Holder name is required"
    )
    @Size(
            min = 3,
            max = 100,
            message = "Holder name must be between 3 and 100 characters"
    )

    private String holderName;

    @NotNull(
            message = "Initial balance is required"
    )
    @PositiveOrZero(
            message = "Initial balance cannot be negative"
    )
    private BigDecimal initialBalance;
}