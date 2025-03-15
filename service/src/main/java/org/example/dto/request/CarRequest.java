package org.example.dto.request;

import lombok.Data;

@Data
public class CarRequest {
    private String vin;
    private String make;
    private String model;
    private Integer year;
}
