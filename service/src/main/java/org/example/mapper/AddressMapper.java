package org.example.mapper;

import org.example.dto.request.AddressRequestDto;
import org.example.dto.response.AddressResponseDto;
import org.example.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponseDto toDto(Address address);
    Address toEntity(AddressRequestDto addressDto);
    void updateEntityFromDto(AddressRequestDto addressRequestDto, @MappingTarget Address address);
}
