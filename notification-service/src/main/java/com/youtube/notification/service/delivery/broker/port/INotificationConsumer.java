package com.youtube.notification.service.delivery.broker.port;

import java.util.function.Consumer;


import com.youtube.notification.service.internal.domain.entities.UserRegisteredEventEntity;

public interface INotificationConsumer {
    public Consumer<UserRegisteredEventEntity> userRegistered();
}
