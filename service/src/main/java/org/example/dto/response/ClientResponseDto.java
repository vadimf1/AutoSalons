package org.example.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.dto.PersonDto;
import org.example.dto.UserDto;

import java.time.LocalDate;

@Data
@Builder
public class ClientResponseDto {

    private Integer id;

    private PersonDto person;

    private UserDto user;

    private AddressResponseDto address;

    private LocalDate birthDate;

    private String passportNumber;
}
