package com.youtube.auth_service.delivery.http;

import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.auth_service.application.port.IAuthService;
import com.youtube.auth_service.delivery.broker.port.IAuthBroker;
import com.youtube.auth_service.delivery.http.port.IAuthController;
import com.youtube.auth_service.internal.domain.dto.UserRequestDTO;
import com.youtube.auth_service.internal.domain.dto.UserResponseDTO;
import com.youtube.auth_service.internal.domain.entities.UserEntity;
import com.youtube.auth_service.pkg.JWTBuilder;
import com.youtube.auth_service.pkg.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController implements IAuthController {

    private final IAuthService authService;
    private final JWTBuilder jwtBuilder;
    private final IAuthBroker authBroker;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequestDTO userDto, HttpServletResponse response) {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(userDto.getEmail());
            userEntity.setHashedPassword(userDto.getPassword().toCharArray());

            UserEntity savedUser = authService.register(userEntity)
                .orElseThrow(() -> new UserAlreadyExistsException("User already exists"));

            authBroker.publishUser(savedUser);

            return authenticateAndRespond(savedUser, userDto.getDeviceId(), response, HttpStatus.CREATED);

        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("[AuthController register] Error: ", e);
            return ResponseEntity.internalServerError().body(Map.of("error", "Internal server error"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequestDTO userDto, HttpServletResponse response) {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(userDto.getEmail());
            userEntity.setHashedPassword(userDto.getPassword().toCharArray());

            UserEntity savedUser = authService.login(userEntity)
                .orElseThrow(() -> new RuntimeException("User not found"));

            return authenticateAndRespond(savedUser, userDto.getDeviceId(), response, HttpStatus.OK);

        } catch (Exception e) {
            log.error("[AuthController login] Error: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

    private ResponseEntity<UserResponseDTO> authenticateAndRespond(UserEntity user, String deviceId, HttpServletResponse response, HttpStatus status) {
        
        Map<String, String> tokens = jwtBuilder.generateTokens(user);
        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        if (accessToken == null || refreshToken == null) {
            throw new RuntimeException("Failed to generate tokens");
        }

        authService.saveToken(user.getId().toString(), deviceId, refreshToken);

        ResponseCookie cookie = ResponseCookie.from("access", accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(900)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        UserResponseDTO authResponse = UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }
}