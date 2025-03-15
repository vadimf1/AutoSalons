package org.example.service;

import org.example.dto.request.PersonRequestDto;
import org.example.dto.response.PersonResponseDto;

import java.util.List;

public interface PersonService {
    void addPerson(PersonRequestDto personDto);
    List<PersonResponseDto> getAllPersons();
    PersonResponseDto getPersonById(int id);
    void updatePerson(int id, PersonRequestDto personDto);
    void deletePersonById(int id);
}
