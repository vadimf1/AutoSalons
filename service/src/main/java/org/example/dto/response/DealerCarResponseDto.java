package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DealerCarResponseDto {
    private Integer id;
    private DealerResponseDto dealer;
    private CarResponseDto car;
    private BigDecimal price;
}
