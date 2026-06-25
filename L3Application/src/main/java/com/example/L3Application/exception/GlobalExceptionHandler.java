package com.example.L3Application.exception;

import com.example.L3Application.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public  class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>>handleApi(ApiException e) {
        log.warn("ApiException [{}]: {}", e.getStatus(),e.getMessage());
        return build(e.getStatus(),e.getMessage(), null);
    }
    private ResponseEntity<ApiResponse<Object>> build(HttpStatus status,Object errors,String message){
        return ResponseEntity.status(status).body(ApiResponse.errors(status.value(),message,errors));
    }
}
