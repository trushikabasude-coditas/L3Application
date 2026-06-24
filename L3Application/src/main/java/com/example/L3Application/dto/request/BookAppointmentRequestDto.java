package com.example.L3Application.dto.request;

import com.example.L3Application.enums.VisitType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookAppointmentRequestDto (
        @NotNull(message="Please Enter a visiting date !!")
        @FutureOrPresent(message = "Date has alredy been Passes put .Please enter the valid date")
        LocalDate visitDate,
        @NotNull(message = "Please  fill the TimeSlot !!")
        LocalTime timeSlot,
        @NotNull(message = "visit type is required")
        VisitType visitType
){
}
