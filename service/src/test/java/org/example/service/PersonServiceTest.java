package org.example.service;

import org.example.dto.request.PersonRequestDto;
import org.example.dto.response.PersonResponseDto;
import org.example.mapper.PersonMapper;
import org.example.model.Person;
import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.example.repository.PersonRepository;
import org.example.service.impl.PersonServiceImpl;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonMapper personMapper;
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person;
    private Contact contact;
    private PersonRequestDto personRequestDto;
    private PersonResponseDto personResponseDto;

    @BeforeEach
    void setUp() {
        person = new Person();
        contact = new Contact();

        personRequestDto = PersonRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .contactIds(List.of(1, 2))
                .build();

        personResponseDto = PersonResponseDto.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    @Test
    void addPerson_ShouldSavePerson() {
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));
        when(contactRepository.findById(2)).thenReturn(Optional.of(contact));
        when(personMapper.toEntity(personRequestDto)).thenReturn(person);

        personService.addPerson(personRequestDto);

        verify(personRepository, times(1)).save(person);
    }

    @Test
    void getAllPersons_ShouldReturnListOfPersons() {
        when(personRepository.findAll()).thenReturn(List.of(person));
        when(personMapper.toDto(person)).thenReturn(personResponseDto);

        List<PersonResponseDto> result = personService.getAllPersons();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void getPersonById_ShouldReturnPerson() {
        when(personRepository.findById(1)).thenReturn(Optional.of(person));
        when(personMapper.toDto(person)).thenReturn(personResponseDto);

        PersonResponseDto result = personService.getPersonById(1);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void updatePerson_ShouldUpdatePerson() {
        when(personRepository.findById(1)).thenReturn(Optional.of(person));
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));
        when(contactRepository.findById(2)).thenReturn(Optional.of(contact));
        doNothing().when(personMapper).updateEntityFromDto(personRequestDto, person);

        personService.updatePerson(1, personRequestDto);

        verify(personRepository, times(1)).save(person);
    }

    @Test
    void deletePersonById_ShouldDeletePerson() {
        when(personRepository.findById(1)).thenReturn(Optional.of(person));

        personService.deletePersonById(1);

        verify(personRepository, times(1)).deleteById(1);
    }
}
