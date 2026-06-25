package com.example.L3Application.service;

import com.example.L3Application.entity.RefreshToken;
import com.example.L3Application.entity.UserEntity;
import org.springframework.security.core.userdetails.User;

public interface RefreshTokenService {
    String create(UserEntity user);
RefreshToken verify(String token);
void delete(String token);
}
