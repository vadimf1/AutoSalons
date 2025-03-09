package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {

    private Integer id;

    @NotNull(message = "Person cannot be null")
    private PersonDto person;

    @NotNull(message = "User cannot be null")
    private UserDto user;

    @NotNull(message = "Auto salon cannot be null")
    private AutoSalonDto autoSalon;

    private AddressDto address;

    @NotBlank(message = "Position cannot be blank")
    @Size(max = 50, message = "Position must not exceed 50 characters")
    private String position;

    @Positive(message = "Salary must be greater than 0")
    private double salary;
}
