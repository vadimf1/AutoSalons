package org.example.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReviewRequestDto {

    @NotNull(message = "ID клиента обязательно")
    private Integer clientId;

    @NotEmpty(message = "Описание не должно быть пустым")
    private String description;

    @NotNull(message = "Рейтинг обязателен")
    @Min(value = 0, message = "Рейтинг должен быть от 0 до 5")
    @Max(value = 5, message = "Рейтинг должен быть от 0 до 5")
    private Integer rating;

    private Integer dealerId;

    private Integer autoSalonId;

    private Integer carId;

    private LocalDate createdAt;
}
