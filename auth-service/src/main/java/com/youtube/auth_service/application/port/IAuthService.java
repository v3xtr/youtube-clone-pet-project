package com.youtube.auth_service.application.port;

import java.util.Optional;

import com.youtube.auth_service.internal.domain.entities.UserEntity;

public interface IAuthService {
    Optional<UserEntity> register(UserEntity userEntity);
    Optional<UserEntity> login(UserEntity userEntity);
    void saveToken(String userId, String deviceId, String refreshToken);
}
