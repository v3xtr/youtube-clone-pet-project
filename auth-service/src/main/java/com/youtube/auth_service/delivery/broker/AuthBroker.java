package com.youtube.auth_service.delivery.broker;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.youtube.auth_service.delivery.broker.port.IAuthBroker;
import com.youtube.auth_service.internal.domain.entities.UserEntity;

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
            boolean sent = streamBridge.send("userRegistered-out-0", userEntity);

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
