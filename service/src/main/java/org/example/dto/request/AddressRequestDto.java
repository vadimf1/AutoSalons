package org.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequestDto {

    @NotBlank(message = "Страна обязательна")
    @Size(max = 50, message = "Страна должна быть не длиннее 50 символов")
    private String country;

    @NotBlank(message = "Город обязателен")
    @Size(max = 100, message = "Город должен быть не длиннее 100 символов")
    private String city;

    @Size(max = 50, message = "Штат/регион должен быть не длиннее 50 символов")
    private String state;

    @NotBlank(message = "Улица обязательна")
    @Size(max = 50, message = "Улица должна быть не длиннее 50 символов")
    private String street;

    @NotBlank(message = "Почтовый индекс обязателен")
    @Pattern(regexp = "\\d{5,10}", message = "Почтовый индекс должен содержать от 5 до 10 цифр")
    private String postalCode;
}
