package com.example.L3Application.controller;

import com.example.L3Application.dto.request.BookAppointmentRequestDto;
import com.example.L3Application.dto.request.IntakeRequest;
import com.example.L3Application.dto.request.PreVisitSummaryRequest;
import com.example.L3Application.dto.request.RescheduleAppointmentRequestDto;
import com.example.L3Application.dto.response.ApiResponse;
import com.example.L3Application.dto.response.AppointmentResponseDto;
import com.example.L3Application.dto.response.AvailableSlotResponseDto;
import com.example.L3Application.entity.User;
import com.example.L3Application.enums.DocumentType;
import com.example.L3Application.service.AppointmentService;
import com.example.L3Application.service.DocumentService;
import com.example.L3Application.service.IntakeService;
import com.example.L3Application.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

/**
 * Everything a patient does with their visit: find a time, book, manage it,
 * complete intake, upload documents, check in, see their queue position, and
 * read/download the visit summary. Patient-only (enforced here + ownership in services).
 */
@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@Tag(name = "Appointments")
public class AppointmentController1 {

    private final AppointmentService appointmentService;
    private final IntakeService intakeService;
    private final DocumentService documentService;
    private final SummaryService summaryService;

    // ----- find a time + book -----

    @Operation(summary = "Available clinic times for a date (only open slots)")
    @GetMapping("/available-slots")
    public ResponseEntity<ApiResponse<AvailableSlotResponseDto>> availableSlots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(),
                "Here are the available slots", appointmentService.getAvailableSlots(date)));
    }

    @Operation(summary = "Book an appointment")
    @PostMapping("/book")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> book(
            @AuthenticationPrincipal User me, @Valid @RequestBody BookAppointmentRequestDto req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
                HttpStatus.CREATED.value(), "Appointment booked", appointmentService.book(me, req)));
    }

    // ----- manage -----

    @Operation(summary = "List my appointments")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> mine(@AuthenticationPrincipal User me) {
        return ResponseEntity.ok(ApiResponse.success(200, "OK", appointmentService.myAppointments(me)));
    }

    @Operation(summary = "Get one of my appointments")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> one(
            @AuthenticationPrincipal User me, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(200, "OK", appointmentService.getMyAppointment(me, id)));
    }

    @Operation(summary = "Reschedule my appointment")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> reschedule(
            @AuthenticationPrincipal User me, @PathVariable Long id,
            @Valid @RequestBody RescheduleAppointmentRequestDto req) {
        return ResponseEntity.ok(ApiResponse.success(200, "Your appointment has been rescheduled",
                appointmentService.reschedule(me, id, req)));
    }

    @Operation(summary = "Cancel my appointment")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancel(@AuthenticationPrincipal User me, @PathVariable Long id) {
        appointmentService.cancel(me, id);
        return ResponseEntity.ok(ApiResponse.success(200, "Your appointment has been cancelled", null));
    }

    @Operation(summary = "Check in (join the queue)")
    @PostMapping("/{id}/check-in")
    public ResponseEntity<ApiResponse<Void>> checkIn(@AuthenticationPrincipal User me, @PathVariable Long id) {
        appointmentService.checkIn(me, id);
        return ResponseEntity.ok(ApiResponse.success(200, "Checked in", null));
    }

    @Operation(summary = "My place in the queue (poll this)")
    @GetMapping("/{id}/queue-position")
    public ResponseEntity<ApiResponse<QueuePositionResponse>> queuePosition(
            @AuthenticationPrincipal User me, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(200, "OK", appointmentService.queuePosition(me, id)));
    }

    // ----- intake -----

    @Operation(summary = "Submit / update my intake")
    @PutMapping("/{id}/intake")
    public ResponseEntity<ApiResponse<IntakeResponse>> saveIntake(
            @AuthenticationPrincipal User me, @PathVariable Long id, @Valid @RequestBody IntakeRequest req) {
        return ResponseEntity.ok(ApiResponse.success(200, "Intake saved", intakeService.save(me, id, req)));
    }

    @Operation(summary = "View my intake")
    @GetMapping("/{id}/intake")
    public ResponseEntity<ApiResponse<IntakeResponse>> getIntake(
            @AuthenticationPrincipal User me, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(200, "OK", intakeService.get(me, id)));
    }

    // ----- documents -----

    @Operation(summary = "Upload a document (ID, insurance, prior record, lab)")
    @PostMapping(value = "/{id}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<DocumentResponse>> upload(
            @AuthenticationPrincipal User me, @PathVariable Long id,
            @RequestParam DocumentType type, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(201, "Uploaded", documentService.upload(me, id, type, file)));
    }

    @Operation(summary = "List my documents")
    @GetMapping("/{id}/documents")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> listDocs(
            @AuthenticationPrincipal User me, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(200, "OK", documentService.list(me, id)));
    }

    @Operation(summary = "Download a document exactly as uploaded")
    @GetMapping("/{id}/documents/{documentId}/download")
    public ResponseEntity<Resource> download(
            @AuthenticationPrincipal User me, @PathVariable Long id, @PathVariable Long documentId) {
        DocumentService.Loaded f = documentService.download(me, documentId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(f.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.filename() + "\"")
                .body(f.resource());
    }

    // ----- summaries -----

    @Operation(summary = "Create a pre-visit summary from a plain-language description")
    @PostMapping("/{id}/pre-visit-summary")
    public ResponseEntity<ApiResponse<SummaryResponse>> preVisit(
            @AuthenticationPrincipal User me, @PathVariable Long id, @Valid @RequestBody PreVisitSummaryRequest req) {
        return ResponseEntity.ok(ApiResponse.success(200, "Pre-visit summary saved",
                summaryService.createPreVisitSummary(me, id, req.description())));
    }

    @Operation(summary = "Read my visit summary")
    @GetMapping("/{id}/visit-summary")
    public ResponseEntity<ApiResponse<SummaryResponse>> visitSummary(
            @AuthenticationPrincipal User me, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(200, "OK",
                summaryService.getVisitSummaryForPatient(me, id)));
    }

    @Operation(summary = "Download my visit summary as a text file")
    @GetMapping("/{id}/visit-summary/download")
    public ResponseEntity<Resource> downloadSummary(
            @AuthenticationPrincipal User me, @PathVariable Long id) {
        SummaryResponse s = summaryService.getVisitSummaryForPatient(me, id);
        String text = "Visit summary\n"
                + "Clinician: " + s.authorName() + "\n"
                + "Date: " + s.createdAt() + "\n\n" + s.content() + "\n";
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"visit-summary-" + id + ".txt\"")
                .contentLength(bytes.length)
                .body(new ByteArrayResource(bytes));
    }
}
