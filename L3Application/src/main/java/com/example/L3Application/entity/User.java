package com.example.L3Application.entity;

import com.example.L3Application.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Column(name = "first_name",nullable = false)
private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;
@Column(nullable = false)
private String password;
@Column(unique = true,nullable = false)
    private String email;
@Column(name = "phone_number",unique = true)
private String phoneNumber;
@Enumerated (EnumType.STRING)
private Roles role;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name ="updated_at")
    private LocalDateTime updatedAt;
    @PrePersist
    void onCreate() {
        this.createdAt = this.updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt=LocalDateTime.now();
    }







}
