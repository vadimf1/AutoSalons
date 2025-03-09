package org.example.mapper;

import org.example.dto.AutoSalonDto;
import org.example.model.AutoSalon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutoSalonMapper {
    AutoSalonDto toDto(AutoSalon autoSalon);
    AutoSalon toEntity(AutoSalonDto autoSalonDto);
}
