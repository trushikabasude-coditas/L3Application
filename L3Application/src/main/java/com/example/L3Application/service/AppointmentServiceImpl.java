package com.example.L3Application.service;

import com.example.L3Application.dto.response.AvailableSlotsResponseDto;
import com.example.L3Application.dto.response.BookAppointmentResponse;
import com.example.L3Application.repo.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
//this service owns the patient facing
public class AppointmentServiceImpl implements AppointmentService {
  private final AppointmentRepository appointmentRepository;
  private final BookClinics bookClinicSlots;
  private final AvailableSlotsResponseDto availableSlotsResponseDto;
  private static List<LocalTime> generateClinicSlots() {
    List<LocalTime> slots=new ArrayList<>();
    LocalTime time = LocalTime.of(9, 0);
    LocalTime end = LocalTime.of(17, 0);
    while (time.isBefore(end)) {
        slots.add(time);
        time=time.plusMinutes(30);
    }
    return List.copyOf(slots);
}


private final BookAppointmentResponse List.isEnable()

}
