package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReviewResponseDto {
    private Integer id;
    private ClientResponseDto client;
    private String description;
    private Integer rating;
    private DealerResponseDto dealer;
    private AutoSalonResponseDto autoSalon;
    private CarResponseDto car;
    private LocalDate createdAt;
}
