package com.example.L3Application.repo;

import com.example.L3Application.entity.Appointment;
import com.example.L3Application.entity.UserEntity;
import com.example.L3Application.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long>{
List<Appointment>findByPatientOrderByVisitDateDescTimeSlotDesc(UserEntity patient);

//booking times for date(to make it no for all pairs and alsoblock 2 at a time booking)
List<Appointment> findAllByVisitDateAndAppointmentStatusNot(LocalDate visitDate, AppointmentStatus status);
boolean existsByVisitDateAndTimeSlotAndAppointmentStatusNot(LocalDate visitDate, LocalTime timeSlot,AppointmentStatus status);
//checkin whover are in order of there tiem
List<Appointment> findByAppointmentStatusOrderByCheckedInAtAsc(AppointmentStatus status);

//booked not remininde for a given day
    List<Appointment> findByAppointmentStatusAndReminderSentFalseAndVisitDate(AppointmentStatus status, LocalDate visitDate);
}
