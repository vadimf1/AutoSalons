package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.ContactDto;
import org.example.exception.ServiceException;
import org.example.mapper.ContactMapper;
import org.example.repository.ContactRepository;
import org.example.service.ContactService;
import org.example.util.error.ContactExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public void addContact(ContactDto contactDto) {
        if (contactDto.getId() != null) {
            throw new ServiceException(ContactExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }
    }

    public List<ContactDto> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toDto)
                .toList();
    }

    public ContactDto getContactById(int id) {
        return contactRepository.findById(id)
                .map(contactMapper::toDto)
                .orElseThrow(() -> new ServiceException(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + id));
    }

    public void updateContact(ContactDto contactDto) {
        contactRepository.save(contactMapper.toEntity(contactDto));
    }

    public void deleteContactById(int id) {
        contactRepository.deleteById(id);
    }
}
