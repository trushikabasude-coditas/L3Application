package com.example.L3Application.dto.response;

public record QueuePositionResponseDto(
        Integer tokenNumber,
        Integer position,     // 1 = next
        Integer totalWaiting
){}
