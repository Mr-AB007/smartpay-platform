package com.smartpay.notification_service.consumer;
import com.smartpay.notification_service.dto.NotificationRequest;
import com.smartpay.notification_service.event.TransactionEvent;
import com.smartpay.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionEventConsumer {

    private final NotificationService notificationService;
    @KafkaListener(
            topics = "transaction-events",
            groupId = "notification-group"
    )
    public void consumeTransactionEvent(
            TransactionEvent event,
            @Header("X-Correlation-Id")
            String correlationId) {

        MDC.put(
                "X-Correlation-Id",
                correlationId
        );

        log.info(
                "Received transaction event for account={} correlationId={}",
                event.getFromAccount(),
                correlationId
        );

        NotificationRequest request = new NotificationRequest();

        request.setRecipient((event.getToAccount()));

        request.setMessage("Transaction successful of account: "+ event.getAmount());

        notificationService.createNotification(request);

        MDC.clear();
    }
}
