package com.youtube.notification.service.pkg;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailCodeGenerator {
    private final SecureRandom secureRandom;

    public int generateCode(){
        int code = secureRandom.nextInt(900000) + 100000;
        
        log.info("[EmailCodeGenerator]: code generated successfully");

        return code;
    }
}
