package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.PersonRequestDto;
import org.example.dto.response.PersonResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.PersonMapper;
import org.example.model.Person;
import org.example.repository.ContactRepository;
import org.example.repository.PersonRepository;
import org.example.service.PersonService;
import org.example.util.error.ContactExceptionCode;
import org.example.util.error.PersonExceptionCode;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final ContactRepository contactRepository;

    public void addPerson(PersonRequestDto personDto) {
        Person person = personMapper.toEntity(personDto);

        addContactsToPerson(person, personDto);

        personRepository.save(person);
    }

    private void addContactsToPerson(Person person, PersonRequestDto personDto) {
        person.setContacts(new HashSet<>());
        for (Integer contactId : personDto.getContactIds()) {
            person.addContact(
                    contactRepository.findById(contactId)
                            .orElseThrow(() -> new ServiceException(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + contactId))
            );
        }
    }

    public List<PersonResponseDto> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toDto)
                .toList();
    }

    public PersonResponseDto getPersonById(int id) {
        return personRepository.findById(id)
                .map(personMapper::toDto)
                .orElseThrow(() -> new ServiceException(PersonExceptionCode.PERSON_NOT_FOUNT_BY_ID.getMessage() + id));
    }

    public void updatePerson(int id, PersonRequestDto personDto) {
        Person person = personRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(PersonExceptionCode.PERSON_NOT_FOUNT_BY_ID.getMessage() + id));

        personMapper.updateEntityFromDto(personDto, person);
        addContactsToPerson(person, personDto);

        personRepository.save(person);
    }

    public void deletePersonById(int id) {
        personRepository.deleteById(id);
    }
}
