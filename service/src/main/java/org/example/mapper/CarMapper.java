package org.example.mapper;

import org.example.dto.CarDto;
import org.example.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDto toDto(Car car);
    @Mapping(target = "autoSalons", ignore = true)
    Car toEntity(CarDto carDto);
}
