package com.example.L3Application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken{
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
private User user;
private Instant expiryDate;
}
