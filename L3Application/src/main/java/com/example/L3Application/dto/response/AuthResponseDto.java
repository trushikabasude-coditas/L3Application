package com.example.L3Application.dto.response;

public record AuthResponseDto(
        String accessToken,
        String refreshToken,
        String tokenType
){
    public static AuthResponseDto of(String access,String refresh){
        return new AuthResponseDto(access,refresh,"Bearer");
    }
}
