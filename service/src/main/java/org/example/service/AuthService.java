package org.example.service;

import org.example.dto.LoginRequestDto;
import org.example.dto.LoginResponseDto;
import org.example.dto.RefreshTokenRequestDto;
import org.example.dto.RefreshTokenResponseDto;
import org.example.dto.RegisterRequestDto;

public interface AuthService {
    void register(RegisterRequestDto registerRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto);
}
