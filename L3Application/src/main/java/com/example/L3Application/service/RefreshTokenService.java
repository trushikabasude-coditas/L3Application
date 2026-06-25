package com.example.L3Application.service;

import com.example.L3Application.entity.RefreshToken;
import org.springframework.security.core.userdetails.User;

public interface RefreshTokenService {
    String create(User user);
RefreshToken verify(String token);
void delete(String token);
}
