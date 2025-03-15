package org.example.mapper;

import org.example.dto.response.DealerResponseDto;
import org.example.model.Dealer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealerMapper {
    DealerResponseDto toDto(Dealer dealer);
    Dealer toEntity(DealerResponseDto dealerDto);
}
