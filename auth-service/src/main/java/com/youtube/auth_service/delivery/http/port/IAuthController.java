package com.youtube.auth_service.delivery.http.port;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.youtube.auth_service.internal.domain.dto.UserRequestDTO;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

public interface IAuthController {
    public ResponseEntity<?> register(@Valid @RequestBody UserRequestDTO userDto, HttpServletResponse response);
    public ResponseEntity<?> login(@Valid @RequestBody UserRequestDTO userDto, HttpServletResponse response);
}
