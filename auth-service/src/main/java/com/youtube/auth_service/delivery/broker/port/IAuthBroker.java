package com.youtube.auth_service.delivery.broker.port;

import com.youtube.auth_service.internal.domain.entities.UserEntity;

public interface IAuthBroker {
    void publishUser(UserEntity userEntity);
}
