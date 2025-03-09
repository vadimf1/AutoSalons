package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.LoginRequestDto;
import org.example.dto.LoginResponseDto;
import org.example.dto.RefreshTokenRequestDto;
import org.example.dto.RefreshTokenResponseDto;
import org.example.dto.RegisterRequestDto;
import org.example.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration")
    public void registration(@RequestBody RegisterRequestDto registerRequestDto) {
        authService.register(registerRequestDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/refresh")
    public RefreshTokenResponseDto refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return authService.refresh(refreshTokenRequestDto);
    }
}
