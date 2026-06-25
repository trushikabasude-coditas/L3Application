package com.example.L3Application.service;

import com.example.L3Application.dto.request.BookAppointmentRequestDto;
import com.example.L3Application.dto.request.RescheduleAppointmentRequestDto;
import com.example.L3Application.dto.response.AppointmentResponseDto;
import com.example.L3Application.dto.response.AvailableSlotResponseDto;
import com.example.L3Application.entity.User;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    AppointmentResponseDto book(User patient, BookAppointmentRequestDto requestDto) throws BadRequestException;

    AvailableSlotResponseDto getAvailableSlots(LocalDate date) throws BadRequestException;

    AppointmentResponseDto reschedule(User  patient, Long id,RescheduleAppointmentRequestDto request) throws BadRequestException;

    void cancel(User user, Long id);

    AppointmentResponseDto getMyAppointment(User patient,Long id);

    List<AppointmentResponseDto> myAppointments(User patient);
}
