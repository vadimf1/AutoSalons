package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.ContactRequestDto;
import org.example.dto.response.ContactResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.ContactMapper;
import org.example.model.Contact;
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

    public void addContact(ContactRequestDto contactDto) {
        contactRepository.save(contactMapper.toEntity(contactDto));
    }

    public List<ContactResponseDto> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toDto)
                .toList();
    }

    public ContactResponseDto getContactById(int id) {
        return contactRepository.findById(id)
                .map(contactMapper::toDto)
                .orElseThrow(() -> new ServiceException(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + id));
    }

    public void updateContact(int id, ContactRequestDto contactDto) {
        Contact contact = contactRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + id));

        contactMapper.updateEntityFromDto(contactDto, contact);

        contactRepository.save(contact);
    }

    public void deleteContactById(int id) {
        contactRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + id));

        contactRepository.deleteById(id);
    }
}
