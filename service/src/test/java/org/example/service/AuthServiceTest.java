package org.example.service;

import org.example.dto.request.LoginRequestDto;
import org.example.dto.request.RefreshTokenRequestDto;
import org.example.dto.request.RegisterRequestDto;
import org.example.dto.response.LoginResponseDto;
import org.example.dto.response.RefreshTokenResponseDto;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.impl.JwtSecurityService;
import org.example.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequestDto registerRequestDto;
    private LoginRequestDto loginRequestDto;
    private RefreshTokenRequestDto refreshTokenRequestDto;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequestDto = RegisterRequestDto.builder()
                .username("testUser")
                .password("password123")
                .build();

        loginRequestDto = LoginRequestDto.builder()
                .username("testUser")
                .password("password123")
                .build();

        refreshTokenRequestDto = RefreshTokenRequestDto.builder()
                .refreshToken("validRefreshToken")
                .build();

        user = User.builder()
                .username("testUser")
                .password("encodedPassword")
                .role(Role.CLIENT)
                .build();
    }

    @Test
    void register_ShouldSaveNewUser() {
        when(passwordEncoder.encode(registerRequestDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        authService.register(registerRequestDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void login_ShouldReturnJwtAndRefreshToken() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtSecurityService.generateToken(userDetails)).thenReturn("jwtToken");
        when(jwtSecurityService.generateRefreshToken(anyMap(), eq(userDetails))).thenReturn("refreshToken");

        LoginResponseDto response = authService.login(loginRequestDto);

        assertNotNull(response);
        assertEquals("jwtToken", response.getJwtToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    void refresh_ShouldReturnNewJwtToken() {
        when(jwtSecurityService.extractUsername("validRefreshToken")).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(jwtSecurityService.validateToken("validRefreshToken", user)).thenReturn(true);
        when(jwtSecurityService.generateToken(user)).thenReturn("newJwtToken");
        when(jwtSecurityService.generateRefreshToken(anyMap(), eq(user))).thenReturn("newRefreshToken");

        RefreshTokenResponseDto response = authService.refresh(refreshTokenRequestDto);

        assertNotNull(response);
        assertEquals("newJwtToken", response.getJwtToken());
        assertEquals("newRefreshToken", response.getRefreshToken());
    }
}
