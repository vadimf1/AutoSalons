package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDto;
import org.example.service.ClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public List<ClientDto> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/passport-number/{passportNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ClientDto getClientByPassport(@PathVariable String passportNumber) {
        return clientService.getClientByPassportNumber(passportNumber);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ClientDto getClientById(@PathVariable int id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void createClient(@Valid @RequestBody ClientDto clientDto) {
        clientService.addClient(clientDto);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void updateClient(@Valid @RequestBody ClientDto updatedClientDto) {
        clientService.updateClient(updatedClientDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteClient(@PathVariable int id) {
        clientService.deleteClientById(id);
    }
}
