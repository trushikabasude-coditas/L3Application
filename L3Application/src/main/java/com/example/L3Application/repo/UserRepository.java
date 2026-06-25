package com.example.L3Application.repo;

import com.example.L3Application.enums.Roles;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User>findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Roles role);
}