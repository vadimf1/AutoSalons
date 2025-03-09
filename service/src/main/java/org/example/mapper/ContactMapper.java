package org.example.mapper;

import org.example.dto.ContactDto;
import org.example.model.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDto toDto(Contact contact);
    Contact toEntity(ContactDto contactDto);
}
