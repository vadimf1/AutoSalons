package org.example.controller;

import org.example.dto.request.PersonRequestDto;
import org.example.dto.response.PersonResponseDto;
import org.example.exception.ServiceException;
import org.example.service.PersonService;
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
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private PersonRequestDto personRequestDto;
    private PersonResponseDto personResponseDto;
    private List<PersonResponseDto> persons;

    @BeforeEach
    void setUp() {
        personRequestDto = PersonRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .contactIds(List.of(1, 2))
                .build();

        personResponseDto = PersonResponseDto.builder()
                .build();

        persons = Arrays.asList(personResponseDto);
    }

    @Test
    void getAllPersons_ShouldReturnListOfPersons() {
        when(personService.getAllPersons()).thenReturn(persons);

        ResponseEntity<List<PersonResponseDto>> response = personController.getAllPersons();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(persons, response.getBody());
        verify(personService).getAllPersons();
    }

    @Test
    void getPersonById_ShouldReturnPerson() {
        when(personService.getPersonById(anyInt())).thenReturn(personResponseDto);

        ResponseEntity<PersonResponseDto> response = personController.getPersonById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(personResponseDto, response.getBody());
        verify(personService).getPersonById(1);
    }

    @Test
    void createPerson_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = personController.addPerson(personRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Person added successfully", response.getBody());
        verify(personService).addPerson(personRequestDto);
    }

    @Test
    void updatePerson_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = personController.updatePerson(1, personRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Person updated successfully", response.getBody());
        verify(personService).updatePerson(1, personRequestDto);
    }

    @Test
    void deletePerson_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = personController.deletePerson(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Person deleted successfully", response.getBody());
        verify(personService).deletePersonById(1);
    }

    @Test
    void getAllPersons_ShouldHandleServiceException() {
        when(personService.getAllPersons()).thenThrow(new ServiceException("Failed to get persons"));

        assertThrows(ServiceException.class, () -> personController.getAllPersons());
        verify(personService).getAllPersons();
    }

    @Test
    void getPersonById_ShouldHandleServiceException() {
        when(personService.getPersonById(anyInt())).thenThrow(new ServiceException("Person not found"));

        assertThrows(ServiceException.class, () -> personController.getPersonById(1));
        verify(personService).getPersonById(1);
    }

    @Test
    void createPerson_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create person")).when(personService).addPerson(any(PersonRequestDto.class));

        assertThrows(ServiceException.class, () -> personController.addPerson(personRequestDto));
        verify(personService).addPerson(personRequestDto);
    }

    @Test
    void updatePerson_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update person")).when(personService).updatePerson(anyInt(), any(PersonRequestDto.class));

        assertThrows(ServiceException.class, () -> personController.updatePerson(1, personRequestDto));
        verify(personService).updatePerson(1, personRequestDto);
    }

    @Test
    void deletePerson_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete person")).when(personService).deletePersonById(anyInt());

        assertThrows(ServiceException.class, () -> personController.deletePerson(1));
        verify(personService).deletePersonById(1);
    }
} 