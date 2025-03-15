package org.example.mapper;

import org.example.dto.request.AutoSalonRequestDto;
import org.example.dto.response.AutoSalonResponseDto;
import org.example.model.AutoSalon;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AutoSalonMapper {
    AutoSalonResponseDto toDto(AutoSalon autoSalon);
    AutoSalon toEntity(AutoSalonRequestDto autoSalonRequestDto);
    void updateEntityFromDto(AutoSalonRequestDto autoSalonRequestDto, @MappingTarget AutoSalon autoSalon);
}
