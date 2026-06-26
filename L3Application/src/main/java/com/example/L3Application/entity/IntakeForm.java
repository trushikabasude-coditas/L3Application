package com.example.L3Application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "intake_form")
public class IntakeForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="appointment_id", unique = true)
    private Appointment appointment;

private String dateOfBirth;
 private String address;
private String emergencyContact;
    @Column(length = 2000)
    private String reasonForVisit;
 @Column(length=4000)
    private String medicalHistory;
    @Column(length = 200)
    private String allergies;
    @Column(length = 200)
    private String currentMedications;
    @Column(name = "intake_complete")
    private boolean intakeComplete;
    @Column(name = "insurance_complete")
    private boolean insuranceComplete;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @PreUpdate @PrePersist
    void touch() { this.updatedAt = LocalDateTime.now(); }
}
