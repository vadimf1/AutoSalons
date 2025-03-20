package org.example.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ReviewFilterRequest {
    private Integer rating;
    private LocalDate createdAt;
}
