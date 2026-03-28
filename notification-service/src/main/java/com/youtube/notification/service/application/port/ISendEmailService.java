package com.youtube.notification.service.application.port;

public interface ISendEmailService {
    void sendPlainText(String to, String code, String userId);
}
