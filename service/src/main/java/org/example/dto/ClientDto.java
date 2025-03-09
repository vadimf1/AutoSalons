package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientDto {

    private Integer id;

    @NotNull(message = "ID персоны обязательно")
    private PersonDto person;

    private UserDto user;

    @NotNull(message = "ID адреса обязательно")
    private AddressDto address;

    @NotNull(message = "Дата рождения обязательна для заполнения")
    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate birthDate;

    @NotNull(message = "Номер паспорта обязателен для заполнения")
    @Size(min = 10, max = 20, message = "Номер паспорта должен быть от 10 до 20 символов")
    private String passportNumber;
}
