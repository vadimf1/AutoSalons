package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.aop.Loggable;
import org.example.dto.LoginRequestDto;
import org.example.dto.LoginResponseDto;
import org.example.dto.RefreshTokenRequestDto;
import org.example.dto.RefreshTokenResponseDto;
import org.example.dto.RegisterRequestDto;
import org.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Loggable
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegisterRequestDto registerRequestDto) {
        authService.register(registerRequestDto);
        return ResponseEntity.ok("Registration successful");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(authService.refresh(refreshTokenRequestDto));
    }
}
