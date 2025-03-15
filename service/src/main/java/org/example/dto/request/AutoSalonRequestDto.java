package org.example.dto.request;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Data
@Builder
public class AutoSalonRequestDto {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    private String name;

    @NotNull(message = "Address ID cannot be null")
    private Integer addressId;

    @NotNull(message = "Contacts cannot be null")
    private List<Integer> contactIds;
}
