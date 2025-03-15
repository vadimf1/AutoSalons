package org.example.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DealerResponseDto {
    private Integer id;

    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    private AddressResponseDto address;
    private List<ContactResponseDto> contacts;
}
