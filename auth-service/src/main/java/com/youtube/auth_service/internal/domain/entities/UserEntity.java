package com.youtube.auth_service.internal.domain.entities;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private UUID id;
    private String email;
    private char[] hashedPassword;
}