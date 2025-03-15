package org.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactRequestDto {

    @NotBlank(message = "Тип контакта не может быть пустым")
    private String contactType;

    @NotBlank(message = "Контактное значение не может быть пустым")
    @Size(max = 255, message = "Контактное значение должно содержать не более 255 символов")
    private String contactValue;
}
