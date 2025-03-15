package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDto {
    private Integer id;

    private String country;

    private String city;

    private String state;

    private String street;

    private String postalCode;
}