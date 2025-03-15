package org.example.mapper;

import org.example.dto.request.DealerCarRequestDto;
import org.example.dto.response.DealerCarResponseDto;
import org.example.model.DealerCar;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DealerCarMapper {
    DealerCarResponseDto toDto(DealerCar dealerCar);
    DealerCar toEntity(DealerCarRequestDto dealerCarDto);
    void updateEntityFromDto(DealerCarRequestDto dealerCarRequestDto, @MappingTarget DealerCar dealerCar);
}
