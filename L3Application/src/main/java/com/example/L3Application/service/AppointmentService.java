package com.example.L3Application.service;

import com.example.L3Application.dto.request.BookAppointmentRequestDto;
import com.example.L3Application.dto.request.RescheduleAppointmentRequestDto;
import com.example.L3Application.dto.response.AppointmentResponseDto;
import com.example.L3Application.dto.response.AvailableSlotsResponseDto;
import com.example.L3Application.entity.User;
import jakarta.validation.Valid;

import java.time.LocalDate;

public interface AppointmentService {

    AppointmentResponseDto book(Object o, @Valid BookAppointmentRequestDto requestDto);

    AvailableSlotsResponseDto getAvailableSlots(@Valid LocalDate date);

    AppointmentResponseDto reschedule(Object o, Long id, @Valid RescheduleAppointmentRequestDto request);

    void cancel(User user, Long id);
}
