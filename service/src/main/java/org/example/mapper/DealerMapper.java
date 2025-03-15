package org.example.mapper;

import org.example.dto.request.DealerRequestDto;
import org.example.dto.response.DealerResponseDto;
import org.example.model.Dealer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DealerMapper {
    DealerResponseDto toDto(Dealer dealer);
    Dealer toEntity(DealerRequestDto dealerDto);
    void updateEntityFromDto(DealerRequestDto dealerRequestDto, @MappingTarget Dealer dealer);
}
