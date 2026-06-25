package com.example.L3Application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank(message = "Enter first anem")
        String firstName,
        String lastName,
        @NotBlank(message = "password is required")
        @Size(min = 6, max = 20, message = "password6to 20 character")
        String password,

        @Email(message = "invalid email")
        @NotBlank(message = "enter emial")
        String email,
        @Size(min = 10, max = 10, message = "only 10 digits")
        String phoneNumber
) {
}
