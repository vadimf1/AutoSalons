package org.example.controller;

import org.example.dto.request.ClientRequestDto;
import org.example.dto.response.ClientResponseDto;
import org.example.exception.ServiceException;
import org.example.model.Person;
import org.example.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ClientRequestDto clientRequestDto;
    private ClientResponseDto clientResponseDto;
    private List<ClientResponseDto> clients;

    @BeforeEach
    void setUp() {
        clientRequestDto = ClientRequestDto.builder()
                .personId(1)
                .userId(2)
                .addressId(3)
                .build();

        clientResponseDto = ClientResponseDto.builder().build();

        clients = Arrays.asList(clientResponseDto);
    }

    @Test
    void getAllClients_ShouldReturnListOfClients() {
        when(clientService.getAllClients()).thenReturn(clients);

        ResponseEntity<List<ClientResponseDto>> response = clientController.getAllClients();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(clients, response.getBody());
        verify(clientService).getAllClients();
    }

    @Test
    void getClientByPassport_ShouldReturnClient() {
        when(clientService.getClientByPassportNumber(anyString())).thenReturn(clientResponseDto);

        ResponseEntity<ClientResponseDto> response = clientController.getClientByPassport("123456");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(clientResponseDto, response.getBody());
        verify(clientService).getClientByPassportNumber("123456");
    }

    @Test
    void getClientById_ShouldReturnClient() {
        when(clientService.getClientById(anyInt())).thenReturn(clientResponseDto);

        ResponseEntity<ClientResponseDto> response = clientController.getClientById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(clientResponseDto, response.getBody());
        verify(clientService).getClientById(1);
    }

    @Test
    void createClient_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = clientController.createClient(clientRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Client created successfully", response.getBody());
        verify(clientService).addClient(clientRequestDto);
    }

    @Test
    void updateClient_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = clientController.updateClient(1, clientRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Client updated successfully", response.getBody());
        verify(clientService).updateClient(1, clientRequestDto);
    }

    @Test
    void deleteClient_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = clientController.deleteClient(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Client deleted successfully", response.getBody());
        verify(clientService).deleteClientById(1);
    }

    @Test
    void getAllClients_ShouldHandleServiceException() {
        when(clientService.getAllClients()).thenThrow(new ServiceException("Failed to get clients"));

        assertThrows(ServiceException.class, () -> clientController.getAllClients());
        verify(clientService).getAllClients();
    }

    @Test
    void getClientByPassport_ShouldHandleServiceException() {
        when(clientService.getClientByPassportNumber(anyString()))
                .thenThrow(new ServiceException("Client not found"));

        assertThrows(ServiceException.class, () -> clientController.getClientByPassport("123456"));
        verify(clientService).getClientByPassportNumber("123456");
    }

    @Test
    void getClientById_ShouldHandleServiceException() {
        when(clientService.getClientById(anyInt()))
                .thenThrow(new ServiceException("Client not found"));

        assertThrows(ServiceException.class, () -> clientController.getClientById(1));
        verify(clientService).getClientById(1);
    }

    @Test
    void createClient_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create client")).when(clientService).addClient(any(ClientRequestDto.class));

        assertThrows(ServiceException.class, () -> clientController.createClient(clientRequestDto));
        verify(clientService).addClient(clientRequestDto);
    }

    @Test
    void updateClient_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update client")).when(clientService).updateClient(anyInt(), any(ClientRequestDto.class));

        assertThrows(ServiceException.class, () -> clientController.updateClient(1, clientRequestDto));
        verify(clientService).updateClient(1, clientRequestDto);
    }

    @Test
    void deleteClient_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete client")).when(clientService).deleteClientById(anyInt());

        assertThrows(ServiceException.class, () -> clientController.deleteClient(1));
        verify(clientService).deleteClientById(1);
    }
} 