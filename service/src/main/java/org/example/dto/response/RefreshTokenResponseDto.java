package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RefreshTokenResponseDto {
    private String jwtToken;
    private String refreshToken;
}
