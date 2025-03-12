package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aop.Loggable;
import org.example.dto.ContactDto;
import org.example.service.ContactService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public List<ContactDto> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ContactDto getContactById(@PathVariable int id) {
        return contactService.getContactById(id);
    }

    @Loggable
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void addContact(@Valid @RequestBody ContactDto contactDto) {
        contactService.addContact(contactDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void updateContact(@Valid @RequestBody ContactDto contactDto) {
        contactService.updateContact(contactDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteContact(@PathVariable("id") int id) {
        contactService.deleteContactById(id);
    }
}
