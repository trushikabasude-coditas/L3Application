package com.example.L3Application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @Email(message = "invalid email")
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "password is required")
        String password
) {}
