package com.example.L3Application.dto.response;

import com.example.L3Application.enums.Roles;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Roles role
){
}
