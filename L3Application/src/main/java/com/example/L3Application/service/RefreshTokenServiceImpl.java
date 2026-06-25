package com.example.L3Application.service;

import com.example.L3Application.entity.RefreshToken;
import com.example.L3Application.entity.UserEntity;
import com.example.L3Application.exception.TokenRefreshException;
import com.example.L3Application.repo.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Service
@RequiredArgsConstructor

public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${app.jwt.refresh-expiration-days}")
   private long refreshExpirationDays;

    @Override
    @Transactional
public String create(UserEntity user){
    RefreshToken token= RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plus(refreshExpirationDays, ChronoUnit.DAYS))
                .build();
refreshTokenRepository.save(token);
return token.getToken();
    }

    @Override
    @Transactional
    public RefreshToken verify(String token) {
        RefreshToken r = refreshTokenRepository.findById(token)
                .orElseThrow(()-> new TokenRefreshException("Invalid Token!! Plkease provide the correct one"));
        if (r.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(r);
            throw new TokenRefreshException("Refresh token expired, please login again");
        }
        return r;
    }
@Override
    @Transactional
public void delete(String token){
refreshTokenRepository.deleteById(token);
}



}
