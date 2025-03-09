package org.example.mapper;

import org.example.dto.DealerCarDto;
import org.example.model.DealerCar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealerCarMapper {
    DealerCarDto toDto(DealerCar dealerCar);
    DealerCar toEntity(DealerCarDto dealerCarDto);
}
