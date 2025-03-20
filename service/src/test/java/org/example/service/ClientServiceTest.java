package org.example.service;

import org.example.dto.request.ClientRequestDto;
import org.example.dto.response.ClientResponseDto;
import org.example.mapper.ClientMapper;
import org.example.model.Address;
import org.example.model.Client;
import org.example.model.Person;
import org.example.model.User;
import org.example.repository.AddressRepository;
import org.example.repository.ClientRepository;
import org.example.repository.PersonRepository;
import org.example.repository.UserRepository;
import org.example.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientRequestDto clientRequestDto;
    private Client client;
    private ClientResponseDto clientResponseDto;
    private Person person;
    private User user;
    private Address address;

    @BeforeEach
    void setUp() {
        clientRequestDto = ClientRequestDto.builder()
                .personId(1)
                .userId(2)
                .addressId(3)
                .birthDate(LocalDate.of(1990, 1, 1))
                .passportNumber("1234567890")
                .build();

        client = new Client();
        client.setPerson(person);
        client.setUser(user);
        client.setAddress(address);
        client.setBirthDate(clientRequestDto.getBirthDate());
        client.setPassportNumber(clientRequestDto.getPassportNumber());

        clientResponseDto = ClientResponseDto.builder()
                .id(1)
                .birthDate(clientRequestDto.getBirthDate())
                .passportNumber(clientRequestDto.getPassportNumber())
                .build();

        person = new Person();
        user = new User();
        address = new Address();
    }

    @Test
    void testAddClient() {
        when(clientMapper.toEntity(clientRequestDto)).thenReturn(client);
        when(personRepository.findById(clientRequestDto.getPersonId())).thenReturn(Optional.of(person));
        when(userRepository.findById(clientRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(addressRepository.findById(clientRequestDto.getAddressId())).thenReturn(Optional.of(address));
        when(clientRepository.save(client)).thenReturn(client);

        clientService.addClient(clientRequestDto);

        verify(clientMapper).toEntity(clientRequestDto);
        verify(personRepository).findById(clientRequestDto.getPersonId());
        verify(userRepository).findById(clientRequestDto.getUserId());
        verify(addressRepository).findById(clientRequestDto.getAddressId());
        verify(clientRepository).save(client);
    }

    @Test
    void testGetAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientResponseDto);

        var result = clientService.getAllClients();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(clientResponseDto, result.get(0));
    }

    @Test
    void testGetClientById() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientResponseDto);

        ClientResponseDto result = clientService.getClientById(1);

        assertNotNull(result);
        assertEquals(clientResponseDto, result);
    }

    @Test
    void testGetClientByPassportNumber() {
        when(clientRepository.findByPassportNumber("1234567890")).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientResponseDto);

        ClientResponseDto result = clientService.getClientByPassportNumber("1234567890");

        assertNotNull(result);
        assertEquals(clientResponseDto, result);
    }

    @Test
    void testUpdateClient() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        doNothing().when(clientMapper).updateEntityFromDto(clientRequestDto, client);
        when(personRepository.findById(clientRequestDto.getPersonId())).thenReturn(Optional.of(person));
        when(userRepository.findById(clientRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(addressRepository.findById(clientRequestDto.getAddressId())).thenReturn(Optional.of(address));
        when(clientRepository.save(client)).thenReturn(client);

        clientService.updateClient(1, clientRequestDto);

        verify(clientRepository).save(client);
        verify(personRepository).findById(clientRequestDto.getPersonId());
        verify(userRepository).findById(clientRequestDto.getUserId());
        verify(addressRepository).findById(clientRequestDto.getAddressId());
    }

    @Test
    void testDeleteClientById() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        clientService.deleteClientById(1);

        verify(clientRepository).deleteById(1);
    }
}
