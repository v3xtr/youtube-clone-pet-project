package com.youtube.auth_service.internal.domain.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEventEntity {
    private Long id;

    private String aggregateId;
    private String payload;
    private String topic;

    private LocalDateTime createdAt;

    private boolean isProcessed = false;
}
