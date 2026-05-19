package com.smartpay.transaction_service.config;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RetryEventLogger {

    private final RetryRegistry retryRegistry;

    @PostConstruct
    public void registerRetryEvents() {

        Retry retry = retryRegistry.retry(
                "accountServiceRetry"
        );

        retry.getEventPublisher()
                .onRetry(event ->
                        {
                            assert event.getLastThrowable() != null;
                            log.warn(
                                    "Retry attempt={} for service={} due to={}",
                                    event.getNumberOfRetryAttempts(),
                                    event.getName(),
                                    event.getLastThrowable().getMessage()
                            );
                        }
                );
    }
}