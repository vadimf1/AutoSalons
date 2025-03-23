package org.example.controller;

import org.example.dto.request.ContactRequestDto;
import org.example.dto.response.ContactResponseDto;
import org.example.exception.ServiceException;
import org.example.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    private ContactRequestDto contactRequestDto;
    private ContactResponseDto contactResponseDto;
    private List<ContactResponseDto> contacts;

    @BeforeEach
    void setUp() {
        contactRequestDto = ContactRequestDto.builder()
                .contactType("PHONE")
                .contactValue("1234567890")
                .build();

        contactResponseDto = ContactResponseDto.builder()
                .build();

        contacts = Arrays.asList(contactResponseDto);
    }

    @Test
    void getAllContacts_ShouldReturnListOfContacts() {
        when(contactService.getAllContacts()).thenReturn(contacts);

        ResponseEntity<List<ContactResponseDto>> response = contactController.getAllContacts();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(contacts, response.getBody());
        verify(contactService).getAllContacts();
    }

    @Test
    void getContactById_ShouldReturnContact() {
        when(contactService.getContactById(anyInt())).thenReturn(contactResponseDto);

        ResponseEntity<ContactResponseDto> response = contactController.getContactById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(contactResponseDto, response.getBody());
        verify(contactService).getContactById(1);
    }

    @Test
    void createContact_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = contactController.addContact(contactRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Contact added successfully", response.getBody());
        verify(contactService).addContact(contactRequestDto);
    }

    @Test
    void updateContact_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = contactController.updateContact(1, contactRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Contact updated successfully", response.getBody());
        verify(contactService).updateContact(1, contactRequestDto);
    }

    @Test
    void deleteContact_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = contactController.deleteContact(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Contact deleted successfully", response.getBody());
        verify(contactService).deleteContactById(1);
    }

    @Test
    void getAllContacts_ShouldHandleServiceException() {
        when(contactService.getAllContacts()).thenThrow(new ServiceException("Failed to get contacts"));

        assertThrows(ServiceException.class, () -> contactController.getAllContacts());
        verify(contactService).getAllContacts();
    }

    @Test
    void getContactById_ShouldHandleServiceException() {
        when(contactService.getContactById(anyInt())).thenThrow(new ServiceException("Contact not found"));

        assertThrows(ServiceException.class, () -> contactController.getContactById(1));
        verify(contactService).getContactById(1);
    }

    @Test
    void createContact_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create contact")).when(contactService).addContact(any(ContactRequestDto.class));

        assertThrows(ServiceException.class, () -> contactController.addContact(contactRequestDto));
        verify(contactService).addContact(contactRequestDto);
    }

    @Test
    void updateContact_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update contact")).when(contactService).updateContact(anyInt(), any(ContactRequestDto.class));

        assertThrows(ServiceException.class, () -> contactController.updateContact(1, contactRequestDto));
        verify(contactService).updateContact(1, contactRequestDto);
    }

    @Test
    void deleteContact_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete contact")).when(contactService).deleteContactById(anyInt());

        assertThrows(ServiceException.class, () -> contactController.deleteContact(1));
        verify(contactService).deleteContactById(1);
    }
} 