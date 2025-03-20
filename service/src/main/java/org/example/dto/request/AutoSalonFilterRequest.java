package org.example.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AutoSalonFilterRequest {
    private String city;
    private String name;
}
