package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TestDriveResponseDto {
    private Integer id;
    private AutoSalonResponseDto autoSalon;
    private ClientResponseDto client;
    private CarResponseDto car;
    private LocalDate testDriveDate;
    private String status;
}
