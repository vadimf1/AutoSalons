package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.ClientRequestDto;
import org.example.dto.response.ClientResponseDto;
import org.example.repository.AddressRepository;
import org.example.repository.ClientRepository;
import org.example.repository.PersonRepository;
import org.example.repository.UserRepository;
import org.example.exception.ServiceException;
import org.example.mapper.ClientMapper;
import org.example.model.Address;
import org.example.model.Client;
import org.example.model.Person;
import org.example.model.User;
import org.example.service.ClientService;
import org.example.util.error.AddressExceptionCode;
import org.example.util.error.ClientExceptionCode;
import org.example.util.error.PersonExceptionCode;
import org.example.util.error.UserExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public List<ClientResponseDto> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDto)
                .toList();
    }

    @Transactional
    public void addClient(ClientRequestDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);

        if (clientDto.getUserId() != null) {
            addUserToClient(client, clientDto.getUserId());
        }

        addRelationsToClient(client, clientDto);

        clientRepository.save(client);
    }

    private void addUserToClient(Client client, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + userId));

        client.setUser(user);
    }

    private void addRelationsToClient(Client client, ClientRequestDto clientDto) {
        Person person = personRepository.findById(clientDto.getPersonId())
                .orElseThrow(() -> new ServiceException(PersonExceptionCode.PERSON_NOT_FOUNT_BY_ID.getMessage() + clientDto.getPersonId()));

        client.setPerson(person);

        Address address = addressRepository.findById(clientDto.getAddressId())
                .orElseThrow(() -> new ServiceException(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + clientDto.getAddressId()));

        client.setAddress(address);
    }

    @Transactional
    public ClientResponseDto getClientById(int clientId) {
        return clientRepository.findById(clientId)
                        .map(clientMapper::toDto)
                        .orElseThrow(() -> new ServiceException(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + clientId));
    }

    @Transactional
    public ClientResponseDto getClientByPassportNumber(String passportNumber) {
        return clientRepository.findByPassportNumber(passportNumber)
                        .map(clientMapper::toDto)
                        .orElseThrow(() -> new ServiceException(ClientExceptionCode.CLIENT_NOT_FOUNT_BY_PASSPORT_NUMBER.getMessage() + passportNumber));
    }

    @Transactional
    public void updateClient(int id, ClientRequestDto clientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + id));

        clientMapper.updateEntityFromDto(clientDto, client);

        if (clientDto.getUserId() != null) {
            addUserToClient(client, clientDto.getUserId());
        }
        addRelationsToClient(client, clientDto);

        clientRepository.save(client);
    }

    @Transactional
    public void deleteClientById(int clientId) {
        clientRepository.findById(clientId)
                        .orElseThrow(() -> new ServiceException(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + clientId));

        clientRepository.deleteById(clientId);
    }
}
