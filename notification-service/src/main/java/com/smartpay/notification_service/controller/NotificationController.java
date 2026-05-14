package com.smartpay.notification_service.controller;

import com.smartpay.notification_service.dto.NotificationRequest;
import com.smartpay.notification_service.entity.Notification;
import com.smartpay.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public Notification sendNotification(
            @RequestBody NotificationRequest request) {

        return notificationService.createNotification(request);
    }
}