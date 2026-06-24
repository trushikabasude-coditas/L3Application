package com.example.L3Application.entity;

import com.example.L3Application.enums.AppointmentStatus;
import com.example.L3Application.enums.VisitType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id",nullable = false,unique = true)
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinician_id")
    private User clinician;

    private LocalDate visitDate;
    private LocalTime timeSlot;

    @Enumerated(EnumType.STRING)
    private VisitType visitType;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    @Column(name = "created-at")

    private LocalDateTime createdAt;

@Column(name = "updated-at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate(){
        this.createdAt=this.updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}