package org.example.service;

import org.example.dto.request.ClientRequestDto;
import org.example.dto.response.ClientResponseDto;

import java.util.List;

public interface ClientService {
    List<ClientResponseDto> getAllClients();
    void addClient(ClientRequestDto clientRequestDto);
    ClientResponseDto getClientById(int clientId);
    void updateClient(int id, ClientRequestDto clientRequestDto);
    void deleteClientById(int clientId);
    ClientResponseDto getClientByPassportNumber(String passportNumber);
}
