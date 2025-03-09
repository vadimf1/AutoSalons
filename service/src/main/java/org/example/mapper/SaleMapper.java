package org.example.mapper;

import org.example.dto.SaleDto;
import org.example.model.Sale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    SaleDto toDto(Sale sale);
    Sale toEntity(SaleDto saleDto);
}
