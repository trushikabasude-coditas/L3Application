package com.example.L3Application.repo;

import com.example.L3Application.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
}
