package org.example.mapper;

import org.example.dto.request.SaleRequestDto;
import org.example.dto.response.SaleResponseDto;
import org.example.model.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    SaleResponseDto toDto(Sale sale);
    Sale toEntity(SaleRequestDto saleDto);
    void updateEntityFromDto(SaleRequestDto saleRequestDto, @MappingTarget Sale sale);
}
