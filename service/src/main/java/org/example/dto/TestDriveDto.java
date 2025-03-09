package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
public class TestDriveDto {

    private Integer id;

    @NotNull(message = "ID автосалона обязателен")
    private AutoSalonDto autoSalon;

    @NotNull(message = "ID автомобиля обязателен")
    private CarDto car;

    @NotNull(message = "ID клиента обязателен")
    private ClientDto client;

    @NotNull(message = "Дата тест-драйва обязательна")
    private LocalDate testDriveDate;

    @Size(min = 3, max = 20, message = "Статус должен быть от 3 до 20 символов")
    private String status;
}
