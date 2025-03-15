package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class SaleResponseDto {
    private Integer id;
    private AutoSalonResponseDto autoSalon;
    private ClientResponseDto client;
    private CarResponseDto car;
    private EmployeeResponseDto employee;
    private LocalDate saleDate;
    private BigDecimal discount;
    private BigDecimal finalPrice;
    private String paymentMethod;
    private String configuration;
    private int warrantyPeriod;
}
