package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aop.Loggable;
import org.example.dto.PersonDto;
import org.example.service.PersonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public List<PersonDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public PersonDto getPersonById(@PathVariable int id) {
        return personService.getPersonById(id);
    }

    @Loggable
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void addPerson(@Valid @RequestBody PersonDto personDto) {
        personService.addPerson(personDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void updatePerson(@Valid @RequestBody PersonDto personDto) {
        personService.updatePerson(personDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePerson(@PathVariable("id") int id) {
        personService.deletePersonById(id);
    }
}
