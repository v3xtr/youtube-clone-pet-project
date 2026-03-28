package com.youtube.notification.service.delivery.broker.port;

public interface ISendEmailCodeProducer {
    public void sendCodeToQueue(String to);
}
