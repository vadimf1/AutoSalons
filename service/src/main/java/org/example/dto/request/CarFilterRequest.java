package org.example.dto.request;

import lombok.Data;

@Data
public class CarFilterRequest {
    private String vin;
    private String make;
    private String model;
    private Integer year;
}
