package org.example.controller;

import org.example.dto.request.LoginRequestDto;
import org.example.dto.request.RefreshTokenRequestDto;
import org.example.dto.request.RegisterRequestDto;
import org.example.dto.response.LoginResponseDto;
import org.example.dto.response.RefreshTokenResponseDto;
import org.example.exception.ServiceException;
import org.example.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private RegisterRequestDto registerRequestDto;
    private LoginRequestDto loginRequestDto;
    private RefreshTokenRequestDto refreshTokenRequestDto;
    private LoginResponseDto loginResponseDto;
    private RefreshTokenResponseDto refreshTokenResponseDto;

    @BeforeEach
    void setUp() {
        registerRequestDto = RegisterRequestDto.builder()
                .username("testuser")
                .password("password123")
                .build();

        loginRequestDto = LoginRequestDto.builder()
                .username("testuser")
                .password("password123")
                .build();

        refreshTokenRequestDto = RefreshTokenRequestDto.builder()
                .refreshToken("refresh-token")
                .build();

        loginResponseDto = LoginResponseDto.builder()
                .jwtToken("access-token")
                .refreshToken("refresh-token")
                .build();

        refreshTokenResponseDto = RefreshTokenResponseDto.builder()
                .jwtToken("new-access-token")
                .refreshToken("new-refresh-token")
                .build();
    }

    @Test
    void registration_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = authController.registration(registerRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Registration successful", response.getBody());
        verify(authService).register(registerRequestDto);
    }

    @Test
    void login_ShouldReturnTokens() {
        when(authService.login(any(LoginRequestDto.class))).thenReturn(loginResponseDto);

        ResponseEntity<LoginResponseDto> response = authController.login(loginRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(loginResponseDto, response.getBody());
        verify(authService).login(loginRequestDto);
    }

    @Test
    void refresh_ShouldReturnNewTokens() {
        when(authService.refresh(any(RefreshTokenRequestDto.class))).thenReturn(refreshTokenResponseDto);

        ResponseEntity<RefreshTokenResponseDto> response = authController.refresh(refreshTokenRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(refreshTokenResponseDto, response.getBody());
        verify(authService).refresh(refreshTokenRequestDto);
    }

    @Test
    void registration_ShouldHandleServiceException() {
        doThrow(new ServiceException("Registration failed")).when(authService).register(any(RegisterRequestDto.class));

        assertThrows(ServiceException.class, () -> authController.registration(registerRequestDto));
        verify(authService).register(registerRequestDto);
    }

    @Test
    void login_ShouldHandleServiceException() {
        when(authService.login(any(LoginRequestDto.class)))
                .thenThrow(new ServiceException("Invalid credentials"));

        assertThrows(ServiceException.class, () -> authController.login(loginRequestDto));
        verify(authService).login(loginRequestDto);
    }

    @Test
    void refresh_ShouldHandleServiceException() {
        when(authService.refresh(any(RefreshTokenRequestDto.class)))
                .thenThrow(new ServiceException("Invalid refresh token"));

        assertThrows(ServiceException.class, () -> authController.refresh(refreshTokenRequestDto));
        verify(authService).refresh(refreshTokenRequestDto);
    }
} 