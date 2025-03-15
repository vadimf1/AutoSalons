package org.example.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TestDriveRequestDto {

    @NotNull(message = "ID автосалона обязателен")
    private Integer autoSalonId;

    @NotNull(message = "ID автомобиля обязателен")
    private Integer carId;

    @NotNull(message = "ID клиента обязателен")
    private Integer clientId;

    @NotNull(message = "Дата тест-драйва обязательна")
    private LocalDate testDriveDate;

    @Size(min = 3, max = 20, message = "Статус должен быть от 3 до 20 символов")
    private String status;
}
