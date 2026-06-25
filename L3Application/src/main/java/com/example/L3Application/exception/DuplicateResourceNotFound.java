package com.example.L3Application.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceNotFound extends ApiException {
    public DuplicateResourceNotFound(String message) {
        super(HttpStatus.CONFLICT,message);

    }
}
