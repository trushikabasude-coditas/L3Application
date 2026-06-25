package com.example.L3Application.service;

import com.example.L3Application.dto.request.LoginDto;
import com.example.L3Application.dto.request.RegisterRequestDto;
import com.example.L3Application.dto.response.AuthResponseDto;
import com.example.L3Application.dto.response.UserResponseDto;
import com.example.L3Application.entity.RefreshToken;
import com.example.L3Application.entity.UserEntity;
import com.example.L3Application.enums.Roles;
import com.example.L3Application.exception.DuplicateResourceNotFound;
import com.example.L3Application.repo.UserRepository;
import com.example.L3Application.security.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class  AuthServiceImpl implements AuthService{
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
   @Override
   @Transactional
    public UserResponseDto register(RegisterRequestDto request) {
        if (userRepo.existsByEmail(request.email())) {
            throw new DuplicateResourceNotFound("Email already registered" + request.email());
        }
        UserEntity user= UserEntity.builder()
               .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(passwordEncoder.encode(request.password()))
                .role(Roles.PATIENT)
                .build();
        userRepo.save(user);
        log.info("Registered user {}", user.getEmail());
        return toDto(user);
    }

    @Override
    @Transactional
    public AuthResponseDto login(LoginDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String accessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = refreshTokenService.create(user);
        log.info("User {} logged in", user.getEmail());
        return AuthResponseDto.of(accessToken, refreshToken);
    }
    @Override
    @Transactional
    public AuthResponseDto refresh(String refreshToken) {
        RefreshToken stored = refreshTokenService.verify(refreshToken);
        UserEntity user = stored.getUser();
        String accessToken = tokenProvider.generateAccessToken(user.getEmail(),user.getRole().name());
        return AuthResponseDto.of(accessToken, stored.getToken());
    }

    @Override
    @Transactional
    public void logout(String refreshToken){
        refreshTokenService.delete(refreshToken);
        log.info("Refresh token invalidated");
    }

    private UserResponseDto toDto(UserEntity u){
        return new UserResponseDto(u.getId(),u.getFirstName(),u.getLastName(),
                u.getEmail(), u.getPhoneNumber(), u.getRole());
    }
}
