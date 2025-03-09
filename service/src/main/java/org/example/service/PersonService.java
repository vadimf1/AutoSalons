package org.example.service;

import org.example.dto.PersonDto;

import java.util.List;

public interface PersonService {
    void addPerson(PersonDto personDto);
    List<PersonDto> getAllPersons();
    PersonDto getPersonById(int id);
    void updatePerson(PersonDto personDto);
    void deletePersonById(int id);
}
