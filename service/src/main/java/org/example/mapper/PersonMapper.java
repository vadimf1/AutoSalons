package org.example.mapper;

import org.example.dto.PersonDto;
import org.example.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDto toDto(Person person);
    Person toEntity(PersonDto personDto);
}
