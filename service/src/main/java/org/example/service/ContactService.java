package org.example.service;

import org.example.dto.request.ContactRequestDto;
import org.example.dto.response.ContactResponseDto;

import java.util.List;

public interface ContactService {
    void addContact(ContactRequestDto contactDto);
    List<ContactResponseDto> getAllContacts();
    ContactResponseDto getContactById(int id);
    void updateContact(int id, ContactRequestDto contactDto);
    void deleteContactById(int id);
}
