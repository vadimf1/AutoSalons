package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String jwtToken;
    private String refreshToken;
}