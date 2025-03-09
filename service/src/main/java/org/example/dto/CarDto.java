package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class CarDto {

    private Integer id;

    @NotBlank(message = "Марка не должна быть пустой")
    @Size(max = 50, message = "Максимальная длина марки — 50 символов")
    private String make;

    @NotBlank(message = "Модель не должна быть пустой")
    @Size(max = 50, message = "Максимальная длина модели — 50 символов")
    private String model;

    @NotBlank(message = "VIN не должен быть пустым")
    @Size(min = 17, max = 17, message = "VIN должен содержать ровно 17 символов")
    @Pattern(regexp = "[A-HJ-NPR-Z0-9]+", message = "VIN содержит недопустимые символы")
    private String vin;

    @Min(value = 1886, message = "Год должен быть не раньше 1886")
    @Max(value = 2025, message = "Год не может быть в будущем")
    private int year;

    @NotBlank(message = "Цвет не должен быть пустым")
    @Size(max = 30, message = "Максимальная длина цвета — 30 символов")
    private String color;

    @NotBlank(message = "Тип кузова не должен быть пустым")
    @Size(max = 30, message = "Максимальная длина типа кузова — 30 символов")
    private String bodyType;

    @NotBlank(message = "Тип двигателя не должен быть пустым")
    @Size(max = 30, message = "Максимальная длина типа двигателя — 30 символов")
    private String engineType;

    @NotBlank(message = "Тип топлива не должен быть пустым")
    @Size(max = 30, message = "Максимальная длина типа топлива — 30 символов")
    private String fuelType;

    @NotBlank(message = "Трансмиссия не должна быть пустой")
    @Size(max = 30, message = "Максимальная длина трансмиссии — 30 символов")
    private String transmission;

    @NotBlank(message = "Статус не должен быть пустым")
    @Size(max = 20, message = "Максимальная длина статуса — 20 символов")
    private String status;

    private List<AutoSalonDto> autoSalons;
}
