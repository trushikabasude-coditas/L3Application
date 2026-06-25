package com.example.L3Application.service;

import com.example.L3Application.dto.response.UserResponseDto;

public interface AuthService{
    UserResponseDto register(RegisterRequestDto request);
    AuthResponseDto login(LoginDto request);
    AuthResponseDto refresh(String refreshToken);
    void logout(String refreshToken);
}
