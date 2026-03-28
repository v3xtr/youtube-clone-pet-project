package com.youtube.notification.service.application;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.youtube.notification.service.application.port.ISendEmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class SendEmailService implements ISendEmailService{
    private final JavaMailSender mailSender;

    public void sendPlainText(String to, String code, String userId){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
    
            message.setTo(to);
            message.setSubject("code");
            message.setText(code);
    
            mailSender.send(message);
            log.info("[SendEmailService sendPlainText]: code has been sent successfully");
            
        } catch (Exception e) {
            log.error("[SendEmailService sendPlainText]: error while sending the code", e);
            throw new RuntimeException("Please retry sending code");
        }
    }
}
