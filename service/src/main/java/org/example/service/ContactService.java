package org.example.service;

import org.example.dto.ContactDto;

import java.util.List;

public interface ContactService {
    void addContact(ContactDto contactDto);
    List<ContactDto> getAllContacts();
    ContactDto getContactById(int id);
    void updateContact(ContactDto contactDto);
    void deleteContactById(int id);
}
