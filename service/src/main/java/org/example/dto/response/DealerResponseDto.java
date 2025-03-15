package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DealerResponseDto {
    private Integer id;
    private String name;
    private AddressResponseDto address;
    private List<ContactResponseDto> contacts;
}
