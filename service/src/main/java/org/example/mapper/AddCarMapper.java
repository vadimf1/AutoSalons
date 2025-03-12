package org.example.mapper;

import org.example.dto.AddCarDto;
import org.example.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddCarMapper {
    AddCarDto toDto(Car car);

    @Mapping(target = "autoSalons", ignore = true)
    Car toEntity(AddCarDto addCarDto);
}
