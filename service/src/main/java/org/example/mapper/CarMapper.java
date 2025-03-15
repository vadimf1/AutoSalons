package org.example.mapper;

import org.example.dto.request.AddCarDto;
import org.example.dto.request.UpdateCarDto;
import org.example.dto.response.CarResponseDto;
import org.example.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarResponseDto toDto(Car car);
    Car toEntity(AddCarDto AddCarDto);
    void updateEntityFromDto(UpdateCarDto updateCarDto, @MappingTarget Car car);
}
