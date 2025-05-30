package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientResponseDto {

    private Integer id;

    private PersonResponseDto person;

    private UserResponseDto user;

    private AddressResponseDto address;

    private LocalDate birthDate;

    private String passportNumber;
}
