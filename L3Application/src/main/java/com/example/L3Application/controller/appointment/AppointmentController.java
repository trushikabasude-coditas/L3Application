package com.example.L3Application.controller.appointment;

import com.example.L3Application.dto.request.BookAppointmentRequestDto;
import com.example.L3Application.dto.request.RescheduleAppointmentRequestDto;
import com.example.L3Application.dto.response.ApiResponse;
import com.example.L3Application.dto.response.AppointmentResponseDto;
import com.example.L3Application.dto.response.AvailableSlotResponseDto;
import com.example.L3Application.entity.User;
import com.example.L3Application.service.AppointmentService;
import com.example.L3Application.service.IntakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@Tag(name="Appointment")

//Appointmnetcontroller is what everything a patient does with the visit like appointment book the slot,intake the form,get summary

public class AppointmentController {

    private final AppointmentService appointmentService;
    private final IntakeService intakeService;
//    private final DocumentService documentService;
//    private final SummaryService summaryService;

  // @ManagedOperation(syummary)
    @GetMapping("/available-slots")
    public ResponseEntity<ApiResponse<AvailableSlotResponseDto>> availableSlots(@Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        AvailableSlotResponseDto slots = appointmentService.getAvailableSlots(date);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Here are the available slots", slots));
    }

    @GetMapping("/book-slot")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> book(@AuthenticationPrincipal User me, @Valid @RequestBody BookAppointmentRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                HttpStatus.CREATED.value(), "Your Appointment is  booked", appointmentService.book(me, requestDto)));
    }
    //also the lsit of all the past apintmnets of that particular user

    //if there is some data whic needs the [past visiting details then patient can search it from the id)
    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getMyAppointments(@AuthenticationPrincipal User me) {
        return ResponseEntity.ok(ApiResponse.success(200, "OK", appointmentService.myAppointments(me)));
    }

    @Operation(summary = "Get one of my appointments")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> getAppointmentById(@AuthenticationPrincipal User me, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(200, "OK", appointmentService.getMyAppointment(me, id)));
    }

    // @Operation(summary = "Reschedule my own appointment")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> reschedule(
            @AuthenticationPrincipal User me ,@PathVariable Long id, @Valid @RequestBody RescheduleAppointmentRequestDto requestDto) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Your appointment has been rescheduled!!", appointmentService.reschedule(me,id,requestDto)));
    }

    //cancel the appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancel(@AuthenticationPrincipal User me, @PathVariable Long id){
        appointmentService.cancel(me,id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Your appointment is successfully cancel!If any help ask us!!", null));
    }

    private User currentUser(Authentication authentication) {
        return (User) authentication.getprincipal();
    }
}
