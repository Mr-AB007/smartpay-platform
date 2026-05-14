package com.smartpay.notification_service.dto;

import lombok.Data;

@Data
public class NotificationRequest {

    private String recipient;

    private String message;
}
