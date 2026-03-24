package com.youtube.auth_service.internal.repo;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.youtube.auth_service.internal.repo.port.IUserRepositoryCache;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCache implements IUserRepositoryCache{

   private final StringRedisTemplate redisTemplate;

    public void saveToken(String redisKey, String refreshToken){
        redisTemplate.opsForValue().set(redisKey, refreshToken, 7, TimeUnit.DAYS);
    }
}
