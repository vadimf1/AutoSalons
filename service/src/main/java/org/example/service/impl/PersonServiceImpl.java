package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.PersonDto;
import org.example.exception.ServiceException;
import org.example.mapper.PersonMapper;
import org.example.repository.PersonRepository;
import org.example.service.PersonService;
import org.example.util.error.PersonExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public void addPerson(PersonDto personDto) {
        if (personDto.getId() != null) {
            throw new ServiceException(PersonExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }
        personRepository.save(personMapper.toEntity(personDto));
    }

    public List<PersonDto> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toDto)
                .toList();
    }

    public PersonDto getPersonById(int id) {
        return personRepository.findById(id)
                .map(personMapper::toDto)
                .orElseThrow(() -> new ServiceException(PersonExceptionCode.PERSON_NOT_FOUNT_BY_ID.getMessage() + id));
    }

    public void updatePerson(PersonDto personDto) {
        personRepository.save(personMapper.toEntity(personDto));
    }

    public void deletePersonById(int id) {
        personRepository.deleteById(id);
    }
}
