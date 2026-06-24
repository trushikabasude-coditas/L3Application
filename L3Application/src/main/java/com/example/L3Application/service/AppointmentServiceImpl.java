package com.example.L3Application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class AppointmentServiceImpl implements AppointmentService {
  private final AppointmentRepository appointmentRepository;

  private final BookClinicSlots bookClinicSlots;

}
