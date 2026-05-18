package com.smartpay.notification_service.service;

import com.smartpay.notification_service.dto.NotificationRequest;
import com.smartpay.notification_service.entity.Notification;
import com.smartpay.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Notification createNotification(
            NotificationRequest request) {

        Notification notification =
                Notification.builder()
                        .recipient(request.getRecipient())
                        .message(request.getMessage())
                        .status("SENT")
                        .createdAt(LocalDateTime.now())
                        .build();

        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotification(){
        return notificationRepository.findAll();
    }
}