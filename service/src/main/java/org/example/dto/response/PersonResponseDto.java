package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonResponseDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<ContactResponseDto> contacts;
}
