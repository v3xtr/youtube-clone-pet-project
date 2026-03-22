package com.youtube.auth_service.internal.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEventEntity {
    private UUID eventId;
    private UUID userId;
    private String email;
    private LocalDateTime createdAt;
}
