package org.example.service;

import org.example.dto.request.ContactRequestDto;
import org.example.dto.response.ContactResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.ContactMapper;
import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.example.service.impl.ContactServiceImpl;
import org.example.util.error.ContactExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactServiceImpl contactService;

    private ContactRequestDto contactRequestDto;
    private Contact contact;
    private ContactResponseDto contactResponseDto;

    @BeforeEach
    void setUp() {
        contactRequestDto = ContactRequestDto.builder()
                .contactType("Email")
                .contactValue("test@example.com")
                .build();

        contact = new Contact();

        contactResponseDto = ContactResponseDto.builder()
                .id(1)
                .contactType("Email")
                .contactValue("test@example.com")
                .build();
    }

    @Test
    void addContact_ShouldSaveContact() {
        when(contactMapper.toEntity(contactRequestDto)).thenReturn(contact);
        when(contactRepository.save(contact)).thenReturn(contact);

        contactService.addContact(contactRequestDto);

        verify(contactRepository).save(contact);
    }

    @Test
    void getAllContacts_ShouldReturnListOfContacts() {
        when(contactRepository.findAll()).thenReturn(List.of(contact));
        when(contactMapper.toDto(contact)).thenReturn(contactResponseDto);

        List<ContactResponseDto> result = contactService.getAllContacts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(contactResponseDto, result.get(0));
    }

    @Test
    void getContactById_ShouldReturnContact() {
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));
        when(contactMapper.toDto(contact)).thenReturn(contactResponseDto);

        ContactResponseDto result = contactService.getContactById(1);

        assertNotNull(result);
        assertEquals(contactResponseDto, result);
    }

    @Test
    void updateContact_ShouldUpdateContact() {
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));
        doNothing().when(contactMapper).updateEntityFromDto(contactRequestDto, contact);
        when(contactRepository.save(contact)).thenReturn(contact);

        contactService.updateContact(1, contactRequestDto);

        verify(contactRepository).save(contact);
    }

    @Test
    void deleteContactById_ShouldDeleteContact() {
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));

        contactService.deleteContactById(1);

        verify(contactRepository).deleteById(1);
    }

    @Test
    void getContactById_ShouldThrowException_WhenContactNotFound() {
        when(contactRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                contactService.getContactById(1));

        assertEquals(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + 1, 
                exception.getMessage());
    }

    @Test
    void updateContact_ShouldThrowException_WhenContactNotFound() {
        when(contactRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                contactService.updateContact(1, contactRequestDto));

        assertEquals(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + 1, 
                exception.getMessage());
    }

    @Test
    void deleteContactById_ShouldThrowException_WhenContactNotFound() {
        when(contactRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                contactService.deleteContactById(1));

        assertEquals(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + 1, 
                exception.getMessage());
    }
}
