package com.youtube.auth_service.internal.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
    
    @NotBlank(message = "Device ID is required for session tracking")
    private String deviceId; 
}