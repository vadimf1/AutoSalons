package org.example.service;

import org.example.dto.ClientDto;
import org.example.model.Client;

import java.util.List;

public interface ClientService {
    List<ClientDto> getAllClients();
    void addClient(ClientDto clientDto);
    ClientDto getClientById(int clientId);
    void updateClient(ClientDto updatedClientDto);
    void deleteClientById(int clientId);
    ClientDto getClientByPassportNumber(String passportNumber);
}
