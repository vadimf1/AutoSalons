package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CarResponseDto {
    private Integer id;
    private String make;
    private String model;
    private String vin;
    private int year;
    private String color;
    private String bodyType;
    private String engineType;
    private String fuelType;
    private String transmission;
    private String status;
    private List<AutoSalonResponseDto> autoSalons;
}
