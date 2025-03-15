package org.example.dto.response;

import lombok.Data;

@Data
public class RefreshTokenResponseDto {
    private String jwtToken;
    private String refreshToken;
}
