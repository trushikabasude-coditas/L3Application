package com.example.L3Application.entity;

import com.example.L3Application.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")

public class UserEntity implements UserDetails {
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

    public String fullName(){
        return lastName==null ? firstName : firstName+lastName;
    }
    @Override
    public String getUsername()
    {
        return email;

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_"+ role.name()));

    }








}
