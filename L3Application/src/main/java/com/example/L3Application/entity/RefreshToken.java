package com.example.L3Application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken{
    @Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
private UserEntity user;
private Instant expiryDate;
}
