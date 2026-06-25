package com.example.L3Application.service;

import com.example.L3Application.dto.request.BookAppointmentRequestDto;
import com.example.L3Application.dto.request.RescheduleAppointmentRequestDto;
import com.example.L3Application.dto.response.AppointmentResponseDto;
import com.example.L3Application.dto.response.AvailableSlotResponseDto;
import com.example.L3Application.dto.response.QueuePositionResponseDto;
import com.example.L3Application.entity.UserEntity;
import org.apache.coyote.BadRequestException;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    AppointmentResponseDto book(UserEntity patient, BookAppointmentRequestDto requestDto) throws BadRequestException;

    AvailableSlotResponseDto getAvailableSlots(LocalDate date) throws BadRequestException;

    AppointmentResponseDto reschedule(UserEntity patient, Long id, RescheduleAppointmentRequestDto request) throws BadRequestException;

    void cancel(UserEntity user, Long id);

    AppointmentResponseDto getMyAppointment(UserEntity patient, Long id);

    List<AppointmentResponseDto> myAppointments(UserEntity patient);

    void checkIn(UserEntity me, Long id);

    QueuePositionResponseDto queuePosition(UserEntity me, Long id);
}
