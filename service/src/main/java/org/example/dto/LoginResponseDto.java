package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String username;
    private String jwtToken;
    private String refreshToken;
}