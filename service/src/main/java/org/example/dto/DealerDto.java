package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class DealerDto {
    private Integer id;

    @NotBlank(message = "Имя не может быть пустым")
    private String name;
}
