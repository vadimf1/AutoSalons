package org.example.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DealerFilterRequest {
    private String city;
    private String name;
}
