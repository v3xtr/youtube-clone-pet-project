package com.youtube.auth_service.application.port;

import com.youtube.auth_service.internal.domain.entities.OutboxEventEntity;

public interface IOutboxService {
    void saveEvent(OutboxEventEntity outboxEventEntity);
}
