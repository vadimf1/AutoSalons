package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.repository.UserRepository;
import org.example.dto.LoginRequestDto;
import org.example.dto.LoginResponseDto;
import org.example.dto.RefreshTokenRequestDto;
import org.example.dto.RefreshTokenResponseDto;
import org.example.dto.RegisterRequestDto;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtSecurityService jwtSecurityService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequestDto registerRequestDto) {
        User user = User.builder()
                        .username(registerRequestDto.getUsername())
                        .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                        .role(Role.CLIENT)
                        .build();
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtSecurityService.generateToken(userDetails);
        String refreshToken = jwtSecurityService.generateRefreshToken(new HashMap<>(), userDetails);

        return LoginResponseDto
                .builder()
                .jwtToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto) {
        String jwt = refreshTokenRequestDto.getRefreshToken();
        String username = jwtSecurityService.extractUsername(jwt);
        User user = userRepository
                .findByUsername(username)
                .orElseThrow();

        if (jwtSecurityService.validateToken(jwt, user)) {
            RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();

            refreshTokenResponseDto.setJwtToken(jwtSecurityService.generateToken(user));
            refreshTokenResponseDto.setRefreshToken(jwtSecurityService.generateRefreshToken(new HashMap<>(), user));

            return refreshTokenResponseDto;
        }

        return null;
    }
}
