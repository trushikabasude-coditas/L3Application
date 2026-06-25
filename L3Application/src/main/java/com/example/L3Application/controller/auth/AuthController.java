package com.example.L3Application.controller.auth;

import com.example.L3Application.dto.request.LoginDto;
import com.example.L3Application.dto.request.RefreshTokenRequestDto;
import com.example.L3Application.dto.request.RegisterRequestDto;
import com.example.L3Application.dto.response.ApiResponse;
import com.example.L3Application.dto.response.AuthResponseDto;
import com.example.L3Application.dto.response.UserResponseDto;
import com.example.L3Application.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController{
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@Valid @RequestBody RegisterRequestDto requestDto){
        UserResponseDto u=authService.register(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED.value(),"User resiterd success!!",u));
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(@Valid @RequestBody LoginDto requestDto){
        AuthResponseDto token=authService.login(requestDto);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.CREATED.value(),"User Loged success!!",token));
    }
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDto>> refresh(@Valid @RequestBody RefreshTokenRequestDto requestDto){
        AuthResponseDto token = authService.refresh(requestDto.refreshToken());
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(),"Token refresh successfully!",token));
    }

    //@Operation(summary = "logiut and invalidate the refresh token")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>>logout(@Valid @RequestBody RefreshTokenRequestDto request){
        authService.logout(request.refreshToken());
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(),"Logged out successfully",null));
    }


}
