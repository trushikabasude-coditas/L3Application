package com.example.L3Application.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record RescheduleAppointmentRequestDto (
        @NotNull(message="Please Enter a visiting date !It is required field.!!")
        @FutureOrPresent(message = "Date has alredy been Passes put .Please enter the valid date")
        LocalDate visitDate,
        @NotNull(message = "Please  fill the TimeSlot !!")
        LocalTime timeSlot
) {

}
