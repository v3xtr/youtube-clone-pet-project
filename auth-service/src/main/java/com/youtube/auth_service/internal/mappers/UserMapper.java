package com.youtube.auth_service.internal.mappers;

import org.springframework.stereotype.Component;

import com.youtube.auth_service.internal.domain.entities.UserEntity;
import com.youtube.auth_service.internal.domain.models.UserModel;

@Component
public class UserMapper {

    public UserEntity toEntity(UserModel userModel) {
        UserEntity userEntity = new UserEntity(); 
        
        userEntity.setId(userModel.getId());
        userEntity.setEmail(userModel.getEmail());
        userEntity.setHashedPassword(userModel.getPassword().toCharArray());
        
        return userEntity;
    }

    public UserModel toModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        
        userModel.setId(userEntity.getId());
        userModel.setEmail(userEntity.getEmail());
        userModel.setPassword(new String(userEntity.getHashedPassword()));
        
        return userModel;
    }
}