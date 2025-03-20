package org.example.service;

import org.example.model.User;
import org.example.service.impl.JwtSecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtSecurityServiceTest {

    private JwtSecurityService jwtSecurityService;
    private UserDetails userDetails;
    private final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private final long ACCESS_TOKEN_EXPIRATION = 3600000;
    private final long REFRESH_TOKEN_EXPIRATION = 86400000;

    @BeforeEach
    void setUp() {
        jwtSecurityService = new JwtSecurityService();
        ReflectionTestUtils.setField(jwtSecurityService, "SECRET_KEY", SECRET_KEY);
        ReflectionTestUtils.setField(jwtSecurityService, "ACCESS_TOKEN_EXPIRATION", ACCESS_TOKEN_EXPIRATION);
        ReflectionTestUtils.setField(jwtSecurityService, "REFRESH_TOKEN_EXPIRATION", REFRESH_TOKEN_EXPIRATION);

        userDetails = User.builder()
                .username("testuser")
                .password("password123")
                .build();
    }

    @Test
    void generateToken_ShouldGenerateValidToken() {
        String token = jwtSecurityService.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void generateRefreshToken_ShouldGenerateValidToken() {
        Map<String, String> claims = new HashMap<>();
        claims.put("type", "refresh");

        String token = jwtSecurityService.generateRefreshToken(claims, userDetails);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        String token = jwtSecurityService.generateToken(userDetails);

        String username = jwtSecurityService.extractUsername(token);

        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void isTokenValid_WithValidToken_ShouldReturnTrue() {
        String token = jwtSecurityService.generateToken(userDetails);

        boolean isValid = jwtSecurityService.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void isTokenValid_WithInvalidUsername_ShouldReturnFalse() {
        String token = jwtSecurityService.generateToken(userDetails);
        UserDetails differentUser = User.builder()
                .username("different")
                .password("password123")
                .build();

        boolean isValid = jwtSecurityService.validateToken(token, differentUser);

        assertFalse(isValid);
    }
} 