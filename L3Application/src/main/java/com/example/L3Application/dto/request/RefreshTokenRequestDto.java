package com.example.L3Application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDto(
        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {
}
