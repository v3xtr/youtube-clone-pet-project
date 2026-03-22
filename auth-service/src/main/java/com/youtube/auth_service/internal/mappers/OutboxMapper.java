package com.youtube.auth_service.internal.mappers;

import com.youtube.auth_service.internal.domain.entities.OutboxEventEntity;
import com.youtube.auth_service.internal.domain.models.OutboxEventModel;

public class OutboxMapper {
    public static OutboxEventModel toModel(OutboxEventEntity entity) {
        return OutboxEventModel.builder()
            .aggregateId(entity.getAggregateId())
            .payload(entity.getPayload())
            .topic(entity.getTopic())
            .createdAt(entity.getCreatedAt())
            .isProcessed(entity.isProcessed())
            .build();
    }
}