package com.example.L3Application.controller.user;

import com.example.L3Application.dto.response.ApiResponse;
import com.example.L3Application.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController @RequestMapping("/user")
    @@RequiredArgsConstructor
    @PreAuthorize("hasRole('PATIENT')")
    @Tag(name = "User")
    public class UserController {
       // @Operation(summary = "My profile")
        @GetMapping("/me")
        public ResponseEntity<ApiResponse<UserResponseDto>> me(@AuthenticationPrincipal User me) {
            UserResponseDto dto = new UserResponseDto(
                    me.getId(), me.getFirstName(), me.getLastName(),
                    me.getEmail(), me.getPhoneNumber(), me.getRole());
            return ResponseEntity.ok(ApiResponse.success(200, "OK", dto));
        }
    }

}
