package com.example.L3Application.controller;

import com.example.L3Application.dto.response.ApiResponse;
import com.example.L3Application.dto.response.AvailableSlotsResponseDto;
import com.example.L3Application.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Operation(summary = "Get avilabkle slots for a date  ")
    @GetMapping("/available-slots")
    public ResponseEntity<ApiResponse<AvailableSlotsResponseDto>> availableSlots(@Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        AvailableSlotsResponseDto slots = appointmentService.getAvailableSlots(date);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Here are the available slots", slots));
    }


}
