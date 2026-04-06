package com.youtube.notification.service.internal.domain.dto;

import java.util.UUID;

public record EmailNotificationEventDTO(
    String email,
    int code,
    UUID userId
) {}