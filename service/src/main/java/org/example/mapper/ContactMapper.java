package org.example.mapper;

import org.example.dto.request.ContactRequestDto;
import org.example.dto.response.ContactResponseDto;
import org.example.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactResponseDto toDto(Contact contact);
    Contact toEntity(ContactRequestDto contactDto);
    void updateEntityFromDto(ContactRequestDto contactRequestDto, @MappingTarget Contact contact);
}
