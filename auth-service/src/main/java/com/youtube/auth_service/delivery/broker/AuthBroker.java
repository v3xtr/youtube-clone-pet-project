package com.youtube.auth_service.delivery.broker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.youtube.auth_service.delivery.broker.port.IAuthBroker;
import com.youtube.auth_service.internal.domain.entities.UserEntity;
import com.youtube.auth_service.internal.domain.entities.UserRegisteredEventEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthBroker implements IAuthBroker{

    private final StreamBridge streamBridge;

    public void publishUser(UserEntity userEntity){
        if(userEntity == null || userEntity.getId() == null){
            log.error("[AuthBroker]: Cannot publish user or ID is null");
            return;
        }

        try {

            UserRegisteredEventEntity event = UserRegisteredEventEntity.builder()
                .eventId(UUID.randomUUID())
                .userId(userEntity.getId())
                .email(userEntity.getEmail())
                .createdAt(LocalDateTime.now())
                .build();

            boolean sent = streamBridge.send("userRegistered-out-0", event);

            if (sent){
                log.info("[AuthBroker]: User {} published successfully to Kafka", userEntity.getId());
            }else{
                log.warn("[AuthBroker]: Failed to send user {} to Kafka (check binder)", userEntity.getId());
            }
        } catch (Exception e) {
            log.error("[AuthBroker publishUser]: cant publish user ", e);
        }
    }
}
