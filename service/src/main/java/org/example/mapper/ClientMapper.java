package org.example.mapper;

import org.example.dto.request.ClientRequestDto;
import org.example.dto.response.ClientResponseDto;
import org.example.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponseDto toDto(Client client);
    Client toEntity(ClientRequestDto clientDto);
    void updateEntityFromDto(ClientRequestDto clientRequestDto, @MappingTarget Client client);
}
