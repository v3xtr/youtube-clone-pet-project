package com.youtube.notification.service.application.port;

import java.util.UUID;

public interface IProcessedEventService {
    public void userRegistered(UUID eventId, String userEmail, UUID userId);
}
