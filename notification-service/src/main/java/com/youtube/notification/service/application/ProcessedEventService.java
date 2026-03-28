package com.youtube.notification.service.application;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.youtube.notification.service.application.port.IProcessedEventService;
import com.youtube.notification.service.delivery.broker.NotificationProducer;
import com.youtube.notification.service.internal.domain.models.ProcessedEvent;
import com.youtube.notification.service.internal.repo.ProcessedEventRepository;
import com.youtube.notification.service.pkg.EmailCodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessedEventService implements IProcessedEventService {
    private final ProcessedEventRepository eventRepository;
    private final EmailCodeGenerator codeGenerator;
    private final NotificationProducer notificationProducer;

    public void userRegistered(UUID eventId, String userEmail, UUID userId) {
        if (eventRepository.existsById(eventId)) return;

        int code = codeGenerator.generateCode();

        this.notificationProducer.sendCodeToQueue(userEmail, code, userId);

        this.eventRepository.save(new ProcessedEvent(eventId, LocalDateTime.now()));
    }
}
