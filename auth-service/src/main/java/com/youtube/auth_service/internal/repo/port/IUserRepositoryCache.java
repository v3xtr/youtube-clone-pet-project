package com.youtube.auth_service.internal.repo.port;


public interface IUserRepositoryCache{
   void saveToken(String redisKey, String refreshToken);
}