package com.example.L3Application.controller;

import com.example.L3Application.dto.request.BookAppointmentRequestDto;
import com.example.L3Application.dto.request.RescheduleAppointmentRequestDto;
import com.example.L3Application.dto.response.ApiResponse;
import com.example.L3Application.dto.response.AppointmentResponseDto;
import com.example.L3Application.dto.response.AvailableSlotsResponseDto;
import com.example.L3Application.dto.response.BookAppointmentResponse;
import com.example.L3Application.entity.User;
import com.example.L3Application.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Operation(summary = "Get available slots for a date  ")
    @GetMapping("/available-slots")
    public ResponseEntity<ApiResponse<AvailableSlotsResponseDto>> availableSlots(@Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        AvailableSlotsResponseDto slots = appointmentService.getAvailableSlots(date);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Here are the available slots", slots));
    }

    @GetMapping("/book-slot")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> book(Authentication authentication, @Valid @RequestBody BookAppointmentRequestDto requestDto) {
        AppointmentResponseDto appointment = appointmentService.book(currentUser(authentication), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED.value(), "Available slots fetched", appointment));
    }

    // @Operation(summary = "Reschedule my own appointment")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> reschedule(
            Authentication authentication, @PathVariable Long id, @Valid @RequestBody RescheduleAppointmentRequestDto request) {
        AppointmentResponseDto appointment = appointmentService.reschedule(currentUser(authentication), id, request);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Your appointment has been rescheduled!!", appointment));
    }

    //cancel the appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancel(Authentication authentication, @PathVariable Long id) {
        appointmentService.cancel(currentUser(authentication), id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Your appointment is successfully deleted", null));
    }

    private User currentUser(Authentication authentication) {
        return (User) authentication.getprincipal();
    }
}
