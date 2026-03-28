package com.youtube.auth_service.application.port;

public interface IKafkaDeliveryService {
    void processOutbox();
}