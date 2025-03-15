package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserResponseDto {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private LocalDate createdAt;
}