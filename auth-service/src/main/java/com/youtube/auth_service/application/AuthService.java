package com.youtube.auth_service.application;

import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.youtube.auth_service.application.port.IAuthService;
import com.youtube.auth_service.internal.domain.entities.UserEntity;
import com.youtube.auth_service.internal.domain.models.UserModel;
import com.youtube.auth_service.internal.mappers.UserMapper;
import com.youtube.auth_service.internal.repo.UserRepository;
import com.youtube.auth_service.internal.repo.port.IUserRepositoryCache;
import com.youtube.auth_service.pkg.PasswordHashing;
import com.youtube.auth_service.pkg.UserAlreadyExistsException;
import com.youtube.auth_service.pkg.UserNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IUserRepositoryCache userRepoCache;
    private final PasswordHashing passwordHashing;

    @Transactional
    public Optional<UserEntity> register(UserEntity userEntity) {
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        String encodedPassword = passwordHashing.passwordEncoder().encode(new String(userEntity.getHashedPassword()));

        userEntity.setHashedPassword(encodedPassword.toCharArray());

        UserModel userModel = userMapper.toModel(userEntity);
        
        if(userModel == null){
            throw new RuntimeException("User cannot be null");
        }

        UserModel savedModel = userRepository.save(userModel);

        return Optional.ofNullable(savedModel).map(userMapper::toEntity);
    }

    @Transactional
    public Optional<UserEntity> login(UserEntity userEntity) {
        UserModel userModel = userRepository.findByEmail(userEntity.getEmail().trim().toLowerCase())
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserEntity dbUserEntity = userMapper.toEntity(userModel);

        String rawPassword = new String(userEntity.getHashedPassword());
        String encodedPasswordFromDb = new String(dbUserEntity.getHashedPassword());

        if (!passwordHashing.passwordEncoder().matches(rawPassword, encodedPasswordFromDb)) {
            throw new BadCredentialsException("Email or password do not match");
        }

        return Optional.of(dbUserEntity);
    }

    public void saveToken(String userId, String deviceId, String refreshToken){
        String redisKey = String.format("refreshToken:%s%s", userId, deviceId);

        if (redisKey == null){
            throw new RuntimeException("redisKey must be provided");
        }

        if (refreshToken == null){
            throw new RuntimeException("refreshToken must be provided");
        }

        userRepoCache.saveToken(redisKey, refreshToken);
    }
}