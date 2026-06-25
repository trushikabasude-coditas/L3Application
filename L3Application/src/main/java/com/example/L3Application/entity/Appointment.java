package com.example.L3Application.entity;

import com.example.L3Application.enums.AppointmentStatus;
import com.example.L3Application.enums.VisitType;
import jakarta.persistence.*;
import jdk.jshell.Snippet;
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
    @JoinColumn(name = "patient_id",nullable = false)//--one patinet can book multiple appointment

    private UserEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)//here at first there will be no clinician later it will be assignerd by the front desk
    @JoinColumn(name = "clinician_id")
    private UserEntity clinician;

    private LocalDate visitDate;
    private LocalTime timeSlot;

    @Enumerated(EnumType.STRING)
    private VisitType visitType;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    @Column(name = "created-at")
    private Snippet reasonForVisit;

    @Column(name = "checked_in_at")
    private LocalDateTime checkedInAt;
    @Column(name = "reminder_sent")
    private boolean reminderSent;
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