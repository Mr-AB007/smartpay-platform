package com.smartpay.transaction_service.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class CorrelationIdFilter implements Filter {

    private static final String CORRELATION_ID = "X-Correlation-Id";

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        String correlationId =
                httpRequest.getHeader(
                        CORRELATION_ID
                );

        if(correlationId == null || correlationId.isBlank()) {

            correlationId = UUID.randomUUID().toString();
        }

        MDC.put(
                CORRELATION_ID,
                correlationId
        );

        log.info(
                "Correlation ID assigned: {}",
                correlationId
        );

        try {
            chain.doFilter(request, response);
        }
        finally {
            MDC.clear();
        }
    }
}