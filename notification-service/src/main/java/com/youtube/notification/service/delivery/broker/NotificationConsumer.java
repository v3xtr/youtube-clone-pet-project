package com.youtube.notification.service.delivery.broker;

import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.context.annotation.Configuration;

import com.youtube.notification.service.application.port.IProcessedEventService;
import com.youtube.notification.service.delivery.broker.port.INotificationConsumer;
import com.youtube.notification.service.internal.domain.entities.UserRegisteredEventEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer implements INotificationConsumer{

    private final IProcessedEventService processedEventService;
        
    public Consumer<UserRegisteredEventEntity> userRegistered() {
        return event -> { 

            UUID eventId = event.getEventId();
            
            log.info("[NotificationConsumer]: received user event {}", eventId);

            this.processedEventService.userRegistered(eventId, event.getEmail(), event.getUserId());

            log.info("[NotificationConsumer userRegistered]: processed event {}", event);



        };
    }
}

