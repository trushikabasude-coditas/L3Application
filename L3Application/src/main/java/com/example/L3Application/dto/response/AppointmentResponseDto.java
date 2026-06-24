package com.example.L3Application.dto.response;

import com.example.L3Application.enums.AppointmentStatus;
import com.example.L3Application.enums.VisitType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AppointmentResponseDto (
        Long id,
        Long patientId,
        String patientName,
        Long clinicianId,
        AppointmentStatus status,
        LocalDate visitDate,
        VisitType visitType,
        LocalTime timeSlot,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}
