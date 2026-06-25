package com.example.L3Application.repo;

import com.example.L3Application.entity.UserEntity;
import com.example.L3Application.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity>findByEmail(String email);
    boolean existsByEmail(String email);
    List<UserEntity> findByRole(Roles role);
}