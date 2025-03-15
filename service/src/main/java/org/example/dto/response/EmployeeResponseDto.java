package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponseDto {
    private Integer id;
    private PersonResponseDto person;
    private UserResponseDto user;
    private AutoSalonResponseDto autoSalon;
    private AddressResponseDto address;
    private String position;
    private double salary;
}
