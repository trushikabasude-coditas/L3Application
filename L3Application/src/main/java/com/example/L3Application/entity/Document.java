package com.example.L3Application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.DocumentType;

import java.time.LocalDateTime;

@Getter
@Setter

public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "appointment_id")
private Appointment appointment;

@Enumerated(EnumType.STRING)
private DocumentType type;
private String originalName;

private String storedName;
private String contentType;
private long size;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @PrePersist
    void onCreate(){
        this.uploadedAt=LocalDateTime.now();
    }
}
