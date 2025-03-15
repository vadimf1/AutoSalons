package org.example.mapper;

import org.example.dto.request.PersonRequestDto;
import org.example.dto.response.PersonResponseDto;
import org.example.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonResponseDto toDto(Person person);
    Person toEntity(PersonRequestDto personDto);
    void updateEntityFromDto(PersonRequestDto personRequestDto, @MappingTarget Person person);
}
