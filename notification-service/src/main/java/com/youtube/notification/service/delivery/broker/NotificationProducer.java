package com.youtube.notification.service.delivery.broker;

import java.util.UUID;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import com.youtube.notification.service.internal.domain.dto.EmailNotificationEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class NotificationProducer {

    private final StreamBridge streamBridge;

    public void sendCodeToQueue(String email, int code, UUID userId) {
        EmailNotificationEvent event = new EmailNotificationEvent(email, code, userId);
        
        log.info("[NotificationProducer]: Sending code {} to Kafka for {}", code, email);
        
        streamBridge.send("sendEmail-out-0", event);
    }
}