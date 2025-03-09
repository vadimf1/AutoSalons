package org.example.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DealerCarDto {

    private Integer id;

    @NotNull(message = "Dealer cannot be null")
    private DealerDto dealer;

    @NotNull(message = "Car cannot be null")
    private CarDto car;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 digits before the decimal and 2 after")
    private BigDecimal price;
}
