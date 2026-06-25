package com.example.L3Application.exception;

import com.example.L3Application.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class TokenRefreshException extends ApiResponse {
    public TokenRefreshException(String message){
        super(HttpStatus.UNAUTHORIZED,message);
    }
}
