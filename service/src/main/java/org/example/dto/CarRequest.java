package org.example.dto;

import lombok.Data;

@Data
public class CarRequest {
    private String vin;
    private String make;
    private String model;
    private Integer year;
}
