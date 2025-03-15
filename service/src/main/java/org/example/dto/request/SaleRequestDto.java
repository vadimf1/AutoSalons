package org.example.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class SaleRequestDto {

    @NotNull(message = "Auto salon cannot be null")
    private Integer autoSalonId;

    @NotNull(message = "Client cannot be null")
    private Integer clientId;

    @NotNull(message = "Car cannot be null")
    private Integer carId;

    @NotNull(message = "Employee cannot be null")
    private Integer employeeId;

    @NotNull(message = "Sale date cannot be null")
    @PastOrPresent(message = "Sale date must be in the past or present")
    private LocalDate saleDate;

    @DecimalMin(value = "0.0", message = "Discount must be at least 0")
    @DecimalMax(value = "1.0", message = "Discount must be at most 1")
    private BigDecimal discount;

    @NotNull(message = "Final price cannot be null")
    @Positive(message = "Final price must be greater than 0")
    private BigDecimal finalPrice;

    @NotBlank(message = "Payment method cannot be blank")
    @Size(max = 50, message = "Payment method must not exceed 50 characters")
    private String paymentMethod;

    @Size(max = 20, message = "Configuration must not exceed 20 characters")
    private String configuration;

    @Min(value = 0, message = "Warranty period must be at least 0")
    private int warrantyPeriod;
}
