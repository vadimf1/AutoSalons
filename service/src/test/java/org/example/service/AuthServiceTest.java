package org.example.service;

import org.example.dto.request.LoginRequestDto;
import org.example.dto.request.RefreshTokenRequestDto;
import org.example.dto.request.RegisterRequestDto;
import org.example.dto.response.LoginResponseDto;
import org.example.dto.response.RefreshTokenResponseDto;
import org.example.exception.ServiceException;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.impl.AuthServiceImpl;
import org.example.service.impl.JwtSecurityService;
import org.example.util.error.UserExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtSecurityService jwtSecurityService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequestDto registerRequestDto;
    private LoginRequestDto loginRequestDto;
    private RefreshTokenRequestDto refreshTokenRequestDto;
    private User user;
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
                .refreshToken("refresh_token")
                .build();

        user = User.builder()
                .id(1)
                .username("testuser")
                .password("encodedPassword")
                .role(Role.CLIENT)
                .build();

        loginResponseDto = LoginResponseDto.builder()
                .jwtToken("jwt_token")
                .refreshToken("refresh_token")
                .build();

        refreshTokenResponseDto = RefreshTokenResponseDto.builder()
                .jwtToken("new_jwt_token")
                .refreshToken("new_refresh_token")
                .build();
    }

    @Test
    void register_ShouldRegisterNewUser() {
        when(passwordEncoder.encode(registerRequestDto.getPassword())).thenReturn("encodedPassword");

        authService.register(registerRequestDto);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_ShouldReturnTokens() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtSecurityService.generateToken(user)).thenReturn("jwt_token");
        when(jwtSecurityService.generateRefreshToken(any(), eq(user))).thenReturn("refresh_token");

        LoginResponseDto result = authService.login(loginRequestDto);

        assertNotNull(result);
        assertEquals("jwt_token", result.getJwtToken());
        assertEquals("refresh_token", result.getRefreshToken());
    }

    @Test
    void refresh_ShouldReturnNewTokens() {
        when(jwtSecurityService.extractUsername(refreshTokenRequestDto.getRefreshToken())).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(jwtSecurityService.validateToken(refreshTokenRequestDto.getRefreshToken(), user)).thenReturn(true);
        when(jwtSecurityService.generateToken(user)).thenReturn("new_jwt_token");
        when(jwtSecurityService.generateRefreshToken(any(), eq(user))).thenReturn("new_refresh_token");

        RefreshTokenResponseDto result = authService.refresh(refreshTokenRequestDto);

        assertNotNull(result);
        assertEquals("new_jwt_token", result.getJwtToken());
        assertEquals("new_refresh_token", result.getRefreshToken());
    }

    @Test
    void login_ShouldThrowException_WhenInvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () ->
                authService.login(loginRequestDto));
    }

    @Test
    void refresh_ShouldReturnNull_WhenInvalidToken() {
        when(jwtSecurityService.extractUsername(refreshTokenRequestDto.getRefreshToken())).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(jwtSecurityService.validateToken(refreshTokenRequestDto.getRefreshToken(), user)).thenReturn(false);

        RefreshTokenResponseDto result = authService.refresh(refreshTokenRequestDto);

        assertNull(result);
    }

    @Test
    void refresh_ShouldThrowException_WhenUserNotFound() {
        when(jwtSecurityService.extractUsername(refreshTokenRequestDto.getRefreshToken())).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                authService.refresh(refreshTokenRequestDto));
    }
}
