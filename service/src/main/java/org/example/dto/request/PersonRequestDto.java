package org.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonRequestDto {

    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 50, message = "Имя не может содержать более 50 символов")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(max = 50, message = "Фамилия не может содержать более 50 символов")
    private String lastName;

    @NotNull(message = "Contacts cannot be null")
    @Size(min = 1, message = "Список контактов не может быть пустым")
    private List<Integer> contactIds;
}
