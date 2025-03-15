package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.example.dto.response.AutoSalonResponseDto;
import org.example.dto.response.CarResponseDto;
import org.example.dto.response.ClientResponseDto;
import org.example.model.Dealer;

import java.time.LocalDate;

@Data
@Builder
public class ReviewDto {

    private Integer id;

    @NotNull(message = "ID клиента обязательно")
    private ClientResponseDto client;

    @NotEmpty(message = "Описание не должно быть пустым")
    private String description;

    @NotNull(message = "Рейтинг обязателен")
    @Min(value = 0, message = "Рейтинг должен быть от 0 до 5")
    @Max(value = 5, message = "Рейтинг должен быть от 0 до 5")
    private Integer rating;

    private LocalDate createdAt;

    private Dealer dealer;

    private AutoSalonResponseDto autoSalon;

    private CarResponseDto car;
}
