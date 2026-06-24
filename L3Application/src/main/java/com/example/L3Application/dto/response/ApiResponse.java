package com.example.L3Application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter@Builder

public class ApiResponse<T> {
private final boolean success;
private final int status;
private final String message;
private final T data;
private final Object errors;

@Builder.Default
    private final LocalDateTime timeStamp=LocalDateTime.now();
public static <T> ApiResponse<T>success(int status, String message,T data) {
    return ApiResponse.<T>builder()
            .success(true)
            .status(status)
            .message(message)
            .data(data)
            .build();
}
    public static  ApiResponse<Object>errors(int status, String message,Object errors) {
        return ApiResponse.builder()
                .success(true)
                .status(status)
                .message(message)
                .errors(errors)
                .build();
    }
}
