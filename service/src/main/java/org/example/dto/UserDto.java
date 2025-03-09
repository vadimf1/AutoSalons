package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserDto {
    private Integer id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 8, max = 255, message = "Имя пользователя должно быть не менее 8 символов")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
    private String password;

    @NotBlank(message = "Роль не может быть пустой")
    private String role;

    private LocalDate createdAt;
}