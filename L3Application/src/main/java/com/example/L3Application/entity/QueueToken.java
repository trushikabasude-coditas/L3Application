package com.example.L3Application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Fallback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

//This is an Waiting Room Token ,When a patient checks in thenthey get the next number of the dayThis is the uniuee token so 2 users can nevr see the token of each other.ANd also front desk coordinator will see all the waiting queuue if the patients based on the issueddate ,tokenId
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "queue_token",uniqueConstraints = @UniqueConstraint(columnNames = {"issued_date","token_number"}))
public class QueueToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
private int tokenNumber;
@Column(name = "issue_date",nullable = false)
private LocalDate issueDate;

@OneToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "appointment_id", unique = true)
private Appointment appointment;

@Column(name = "issued-at")
private LocalDateTime  issuedAt;


}
