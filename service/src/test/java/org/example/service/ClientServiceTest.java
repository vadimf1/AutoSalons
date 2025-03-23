package org.example.service;

import org.example.dto.request.ClientRequestDto;
import org.example.dto.response.ClientResponseDto;
import org.example.exception.ServiceException;
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
import org.example.util.error.AddressExceptionCode;
import org.example.util.error.ClientExceptionCode;
import org.example.util.error.PersonExceptionCode;
import org.example.util.error.UserExceptionCode;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private Person person;
    private User user;
    private Address address;
    private ClientResponseDto clientResponseDto;

    @BeforeEach
    void setUp() {
        clientRequestDto = ClientRequestDto.builder()
                .personId(1)
                .userId(2)
                .addressId(3)
                .birthDate(LocalDate.of(1990, 1, 1))
                .passportNumber("1234567890")
                .build();

        person = new Person();
        user = new User();
        address = new Address();
        client = new Client();

        clientResponseDto = ClientResponseDto.builder()
                .id(1)
                .birthDate(LocalDate.of(1990, 1, 1))
                .passportNumber("1234567890")
                .build();
    }

    @Test
    void addClient_ShouldSaveClient() {
        when(clientMapper.toEntity(clientRequestDto)).thenReturn(client);
        when(personRepository.findById(clientRequestDto.getPersonId())).thenReturn(Optional.of(person));
        when(userRepository.findById(clientRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(addressRepository.findById(clientRequestDto.getAddressId())).thenReturn(Optional.of(address));
        when(clientRepository.save(client)).thenReturn(client);

        clientService.addClient(clientRequestDto);

        verify(clientRepository).save(client);
    }

    @Test
    void getAllClients_ShouldReturnListOfClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientResponseDto);

        List<ClientResponseDto> result = clientService.getAllClients();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(clientResponseDto, result.get(0));
    }

    @Test
    void getClientById_ShouldReturnClient() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientResponseDto);

        ClientResponseDto result = clientService.getClientById(1);

        assertNotNull(result);
        assertEquals(clientResponseDto, result);
    }

    @Test
    void updateClient_ShouldUpdateClient() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(personRepository.findById(clientRequestDto.getPersonId())).thenReturn(Optional.of(person));
        when(userRepository.findById(clientRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(addressRepository.findById(clientRequestDto.getAddressId())).thenReturn(Optional.of(address));
        doNothing().when(clientMapper).updateEntityFromDto(clientRequestDto, client);
        when(clientRepository.save(client)).thenReturn(client);

        clientService.updateClient(1, clientRequestDto);

        verify(clientRepository).save(client);
    }

    @Test
    void deleteClientById_ShouldDeleteClient() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        clientService.deleteClientById(1);

        verify(clientRepository).deleteById(1);
    }

    @Test
    void addClient_ShouldThrowException_WhenPersonNotFound() {
        when(clientMapper.toEntity(clientRequestDto)).thenReturn(client);
        when(userRepository.findById(clientRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(personRepository.findById(clientRequestDto.getPersonId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                clientService.addClient(clientRequestDto));

        assertEquals(PersonExceptionCode.PERSON_NOT_FOUNT_BY_ID.getMessage() + clientRequestDto.getPersonId(),
                exception.getMessage());
    }

    @Test
    void addClient_ShouldThrowException_WhenUserNotFound() {
        when(clientMapper.toEntity(clientRequestDto)).thenReturn(client);
        when(userRepository.findById(clientRequestDto.getUserId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                clientService.addClient(clientRequestDto));

        assertEquals(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + clientRequestDto.getUserId(),
                exception.getMessage());
    }

    @Test
    void addClient_ShouldThrowException_WhenAddressNotFound() {
        when(clientMapper.toEntity(clientRequestDto)).thenReturn(client);
        when(personRepository.findById(clientRequestDto.getPersonId())).thenReturn(Optional.of(person));
        when(userRepository.findById(clientRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(addressRepository.findById(clientRequestDto.getAddressId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                clientService.addClient(clientRequestDto));

        assertEquals(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + clientRequestDto.getAddressId(),
                exception.getMessage());
    }

    @Test
    void getClientById_ShouldThrowException_WhenClientNotFound() {
        when(clientRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                clientService.getClientById(1));

        assertEquals(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void getClientByPassportNumber_ShouldThrowException_WhenClientNotFound() {
        String passportNumber = "1234567890";
        when(clientRepository.findByPassportNumber(passportNumber)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                clientService.getClientByPassportNumber(passportNumber));

        assertEquals(ClientExceptionCode.CLIENT_NOT_FOUNT_BY_PASSPORT_NUMBER.getMessage() + passportNumber,
                exception.getMessage());
    }

    @Test
    void updateClient_ShouldThrowException_WhenClientNotFound() {
        when(clientRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                clientService.updateClient(1, clientRequestDto));

        assertEquals(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void updateClient_ShouldThrowException_WhenPersonNotFound() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(userRepository.findById(clientRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(personRepository.findById(clientRequestDto.getPersonId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                clientService.updateClient(1, clientRequestDto));

        assertEquals(PersonExceptionCode.PERSON_NOT_FOUNT_BY_ID.getMessage() + clientRequestDto.getPersonId(),
                exception.getMessage());
    }

    @Test
    void updateClient_ShouldThrowException_WhenUserNotFound() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(userRepository.findById(clientRequestDto.getUserId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                clientService.updateClient(1, clientRequestDto));

        assertEquals(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + clientRequestDto.getUserId(),
                exception.getMessage());
    }

    @Test
    void updateClient_ShouldThrowException_WhenAddressNotFound() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(personRepository.findById(clientRequestDto.getPersonId())).thenReturn(Optional.of(person));
        when(userRepository.findById(clientRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(addressRepository.findById(clientRequestDto.getAddressId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                clientService.updateClient(1, clientRequestDto));

        assertEquals(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + clientRequestDto.getAddressId(),
                exception.getMessage());
    }

    @Test
    void deleteClientById_ShouldThrowException_WhenClientNotFound() {
        when(clientRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                clientService.deleteClientById(1));

        assertEquals(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }
}
