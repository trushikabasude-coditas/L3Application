package com.example.L3Application.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenProvider  {
    String generateAccessToken(String username, String role);
    String extractUsername(String token);
boolean isTokenValid(String token, UserDetails user);

}
