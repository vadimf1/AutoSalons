package org.example.service;

import org.example.dto.request.LoginRequestDto;
import org.example.dto.response.LoginResponseDto;
import org.example.dto.request.RefreshTokenRequestDto;
import org.example.dto.response.RefreshTokenResponseDto;
import org.example.dto.request.RegisterRequestDto;

public interface AuthService {
    void register(RegisterRequestDto registerRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto);
}
