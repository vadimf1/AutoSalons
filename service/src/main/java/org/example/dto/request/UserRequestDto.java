package org.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 8, max = 255, message = "Имя пользователя должно быть не менее 8 символов")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
    private String password;

    @NotBlank(message = "Роль не может быть пустой")
    private String role;
}
