package com.youtube.notification.service.delivery.broker;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.kafka.common.Uuid;
import org.aspectj.bridge.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.youtube.notification.service.delivery.broker.port.INotificationConsumer;
import com.youtube.notification.service.internal.domain.entities.UserRegisteredEventEntity;
import com.youtube.notification.service.internal.domain.models.ProcessedEvent;
import com.youtube.notification.service.internal.repo.ProcessedEventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer implements INotificationConsumer{
    
    private final ProcessedEventRepository eventRepository;

    @Bean
    public Consumer<UserRegisteredEventEntity> userRegistered() {
        return event -> { 

            UUID eventId = event.getEventId();

            log.info("[NotificationConsumer]: received user event {}", eventId);

            if (eventRepository.existsById(eventId)) {
                log.warn("[NotificationConsumer]: Event {} already processed. Skipping.", eventId);
                return;
            }

            try {
                sendEmail(event.getEmail(), "Your code is: " + event.getUserId());
                
                eventRepository.save(new ProcessedEvent(eventId, LocalDateTime.now()));
                
            } catch (Exception e) {
                log.error("[NotificationConsumer]: error while sending email: {}", e.getMessage());
                throw new RuntimeException("Retry please!");
            }
        };
    }

    private void sendEmail(String email, String message){
        
    }
}

