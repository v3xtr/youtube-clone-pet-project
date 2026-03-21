package com.youtube.auth_service.internal.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youtube.auth_service.internal.domain.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID>{
    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);

}
