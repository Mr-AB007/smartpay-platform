package com.smartpay.notification_service.consumer;
import com.smartpay.notification_service.dto.NotificationRequest;
import com.smartpay.notification_service.event.TransactionEvent;
import com.smartpay.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
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
    public void consume(TransactionEvent event){
        log.info("Received transaction event :{}",event);

        NotificationRequest request = new NotificationRequest();

        request.setRecipient((event.getToAccount()));

        request.setMessage("Transaction successful of account: "+ event.getAmount());

        notificationService.createNotification(request);
    }
}
