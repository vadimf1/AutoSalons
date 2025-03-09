package org.example.mapper;

import org.example.dto.DealerDto;
import org.example.model.Dealer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealerMapper {
    DealerDto toDto(Dealer dealer);
    Dealer toEntity(DealerDto dealerDto);
}
