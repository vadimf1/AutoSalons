package org.example.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.impl.JwtSecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtSecurityServiceTest {

    @InjectMocks
    private JwtSecurityService jwtSecurityService;

    private User user;
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long ACCESS_TOKEN_EXPIRATION = 3600000;
    private static final long REFRESH_TOKEN_EXPIRATION = 86400000;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .username("testuser")
                .password("password")
                .role(Role.CLIENT)
                .build();

        ReflectionTestUtils.setField(jwtSecurityService, "SECRET_KEY", SECRET_KEY);
        ReflectionTestUtils.setField(jwtSecurityService, "ACCESS_TOKEN_EXPIRATION", ACCESS_TOKEN_EXPIRATION);
        ReflectionTestUtils.setField(jwtSecurityService, "REFRESH_TOKEN_EXPIRATION", REFRESH_TOKEN_EXPIRATION);
    }

    @Test
    void generateToken_ShouldGenerateValidToken() {
        String token = jwtSecurityService.generateToken(user);

        assertNotNull(token);
        assertTrue(jwtSecurityService.validateToken(token, user));
        assertEquals(user.getUsername(), jwtSecurityService.extractUsername(token));
    }

    @Test
    void generateRefreshToken_ShouldGenerateValidToken() {
        Map<String, String> claims = new HashMap<>();
        claims.put("type", "refresh");
        
        String refreshToken = jwtSecurityService.generateRefreshToken(claims, user);

        assertNotNull(refreshToken);
        assertTrue(jwtSecurityService.validateToken(refreshToken, user));
        assertEquals(user.getUsername(), jwtSecurityService.extractUsername(refreshToken));
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        String token = jwtSecurityService.generateToken(user);
        String username = jwtSecurityService.extractUsername(token);

        assertEquals(user.getUsername(), username);
    }

    @Test
    void validateToken_ShouldReturnTrue_WhenTokenIsValid() {
        String token = jwtSecurityService.generateToken(user);

        assertTrue(jwtSecurityService.validateToken(token, user));
    }

    @Test
    void validateToken_ShouldReturnFalse_WhenUsernameDoesNotMatch() {
        String token = jwtSecurityService.generateToken(user);

        User anotherUser = User.builder()
                .id(2)
                .username("anotheruser")
                .password("password")
                .role(Role.CLIENT)
                .build();

        assertFalse(jwtSecurityService.validateToken(token, anotherUser));
    }

    @Test
    void extractUsername_ShouldThrowException_WhenTokenIsInvalid() {
        String invalidToken = "invalid.token.string";

        assertThrows(MalformedJwtException.class, () ->
                jwtSecurityService.extractUsername(invalidToken));
    }

    @Test
    void extractUsername_ShouldThrowException_WhenTokenIsExpired() {
        ReflectionTestUtils.setField(jwtSecurityService, "ACCESS_TOKEN_EXPIRATION", 1);
        String token = jwtSecurityService.generateToken(user);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertThrows(ExpiredJwtException.class, () ->
                jwtSecurityService.extractUsername(token));
    }

    @Test
    void extractExpiration_ShouldReturnCorrectDate() {
        String token = jwtSecurityService.generateToken(user);
        Date expiration = jwtSecurityService.extractExpiration(token);

        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void isTokenExpired_ShouldReturnFalse_WhenTokenIsValid() {
        String token = jwtSecurityService.generateToken(user);

        assertFalse(jwtSecurityService.isTokenExpired(token));
    }
} 