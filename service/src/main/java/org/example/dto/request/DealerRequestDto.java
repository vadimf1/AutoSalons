package org.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DealerRequestDto {

    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @NotNull(message = "Address cannot be null")
    private Integer addressId;

    @NotNull(message = "Contacts cannot be null")
    @Size(min = 1, message = "Список контактов не может быть пустым")
    private List<Integer> contactIds;
}
