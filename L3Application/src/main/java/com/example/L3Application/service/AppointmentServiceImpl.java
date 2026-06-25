package com.example.L3Application.service;

import com.example.L3Application.dto.request.BookAppointmentRequestDto;
import com.example.L3Application.dto.request.RescheduleAppointmentRequestDto;
import com.example.L3Application.dto.response.AppointmentResponseDto;
import com.example.L3Application.dto.response.AvailableSlotResponseDto;
import com.example.L3Application.dto.response.BookAppointmentResponse;
import com.example.L3Application.email.EmailService;
import com.example.L3Application.entity.Appointment;
import com.example.L3Application.entity.User;
import com.example.L3Application.enums.AppointmentStatus;
import com.example.L3Application.exception.ConflictException;
import com.example.L3Application.repo.AppointmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
//this service owns the patient facing

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final QueueService queueService;
    private final EmailService emailService;


    private static List<LocalTime> generateClinicSlots() {
        List<LocalTime>slots = new ArrayList<>();
        LocalTime time = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);
        while (time.isBefore(end)) {
            slots.add(time);
            time = time.plusMinutes(30);
        }
        return slots;
    }
    @Override
    public AppointmentResponseDto book(User patient, BookAppointmentRequestDto requestDto) {

if(!generateClinicSlots().contains(requestDto.timeSlot())){
throw new BadRequestException("Clinic is closed at this time.!");
}
//checking if that slot is vaccant or not
boolean taken= appointmentRepository.existsByVisitDateAndTimeSlotAndAppointmentStatusNot(
        requestDto.visitDate(),requestDto.timeSlot(),AppointmentStatus.CANCELLED);
        if (taken) {
            throw new ConflictException("That time slot was just taken. Please pick another.");
        }
        Appointment appointment =Appointment.builder()
                .patient(patient)
                .visitDate(requestDto.visitDate())
                .timeSlot(requestDto.timeSlot())
                .visitType(requestDto.visitType())
                .appointmentStatus(AppointmentStatus.BOOKED)
                .build();
        appointmentRepository.save(appointment);
        emailService.sendBookingConfirmation(patient.getEmail(), patient.fullName(),
                appointment.getVisitDate(),appointment.getTimeSlot());
        return toResponse(appointment);
    }

    private AppointmentResponseDto toResponse(Appointment appointment) {
return new AppointmentResponseDto(
        appointment.getId(),
        appointment.getPatient().getId(),
        appointment.getPatient().fullName(),
        appointment.getClinician()!=null ? appointment.getClinician().getId():null,
        appointment.getAppointmentStatus(),
        appointment.getVisitDate(),
        appointment.getVisitType(),
        appointment.getTimeSlot(),
        appointment.getCreatedAt(),
        appointment.getUpdatedAt());

    }

    @Override
    public AvailableSlotResponseDto getAvailableSlots(LocalDate date) throws BadRequestException {
        if(date==null||date.isBefore(LocalDate.now())){
            throw new BadRequestException("please choses today or future dates!!");
        }
        List<LocalTime> takenSlots=appointmentRepository.findAllByVisitDateAndAppointmentStatusNot(date,AppointmentStatus.CANCELLED)
                .stream().map(Appointment::getTimeSlot).toList();
List<LocalTime> open =new ArrayList<>();
for(LocalTime time: generateClinicSlots()) {
    if (!takenSlots.contains(time)) open.add(time);//here only the yes comes in
}
return new AvailableSlotResponseDto(date,open);



    }

    @Override
    public AppointmentResponseDto reschedule(User patient, Long id, RescheduleAppointmentRequestDto request) {
        return null;
    }

    @Override
    public void cancel(User user, Long id) {

    }

    @Override
    @Transactional(readOnly=true)
    public AppointmentResponseDto getMyAppointment(User patient, Long id) {
        return toResponse(isThisMyAppointmnet(id,patient));
    }

    private Appointment isThisMyAppointmnet(Long id, User patient) {
Appointment appointment=appointmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Appointment Not Found"));
if(appointment.getAppointmnetStatus)
    }

    @Override
    @Transactional(readOnly=true)
    public List<AppointmentResponseDto> myAppointments(User patient) {
        return java.util.List.of();
    }
}
