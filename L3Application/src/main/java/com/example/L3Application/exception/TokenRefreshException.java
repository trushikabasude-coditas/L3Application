package com.example.L3Application.exception;

import org.springframework.http.HttpStatus;

public class TokenRefreshException extends ApiException {
    public TokenRefreshException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
