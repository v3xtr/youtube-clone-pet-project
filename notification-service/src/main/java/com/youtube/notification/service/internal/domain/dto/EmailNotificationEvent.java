package com.youtube.notification.service.internal.domain.dto;

import java.util.UUID;

public record EmailNotificationEvent(
    String email,
    int code,
    UUID userId
) {}