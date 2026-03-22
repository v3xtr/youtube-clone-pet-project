package com.youtube.notification.service.internal.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEventEntity {
    private UUID eventId;
    private UUID userId;
    private String email;
    private LocalDateTime createdAt;
}
