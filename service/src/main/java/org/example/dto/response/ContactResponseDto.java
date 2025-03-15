package org.example.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactResponseDto {
    private Integer id;
    private String contactType;
    private String contactValue;
}
