package org.example.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenRequestDto {
    private String refreshToken;
}
