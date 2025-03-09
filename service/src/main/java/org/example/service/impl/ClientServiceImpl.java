package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.repository.AddressRepository;
import org.example.repository.ClientRepository;
import org.example.repository.PersonRepository;
import org.example.repository.UserRepository;
import org.example.dto.ClientDto;
import org.example.exception.ServiceException;
import org.example.mapper.AddressMapper;
import org.example.mapper.ClientMapper;
import org.example.mapper.PersonMapper;
import org.example.mapper.UserMapper;
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
    private final UserMapper userMapper;
    private final PersonMapper personMapper;
    private final AddressMapper addressMapper;

    @Transactional
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDto)
                .toList();
    }

    @Transactional
    public void addClient(ClientDto clientDto) {
        if (clientDto.getId() != null) {
            throw new ServiceException(ClientExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }

        Client client = clientMapper.toEntity(clientDto);

        if (clientDto.getUser() != null) {
            User user;
            if (clientDto.getUser().getId() != null) {
                user = userRepository.findById(clientDto.getUser().getId())
                        .orElseThrow(() -> new ServiceException(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + clientDto.getUser().getId()));
            } else {
                user = userMapper.toEntity(clientDto.getUser());
                userRepository.save(user);
            }
            client.setUser(user);
        }

        Person person;
        if (clientDto.getPerson().getId() != null) {
            person = personRepository.findById(clientDto.getPerson().getId())
                    .orElseThrow(() -> new ServiceException(PersonExceptionCode.PERSON_NOT_FOUNT_BY_ID.getMessage() + clientDto.getPerson().getId()));
        } else {
            person = personMapper.toEntity(clientDto.getPerson());
            personRepository.save(person);
        }
        client.setPerson(person);

        Address address;
        if (clientDto.getAddress().getId() != null) {
            address = addressRepository.findById(clientDto.getAddress().getId())
                    .orElseThrow(() -> new ServiceException(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + clientDto.getAddress().getId()));
        } else {
            address = addressMapper.toEntity(clientDto.getAddress());
            addressRepository.save(address);
        }
        client.setAddress(address);

        clientRepository.save(client);
    }

    @Transactional
    public ClientDto getClientById(int clientId) {
        return clientRepository.findById(clientId)
                        .map(clientMapper::toDto)
                        .orElseThrow(() -> new ServiceException(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + clientId));
    }

    @Transactional
    public ClientDto getClientByPassportNumber(String passportNumber) {
        return clientRepository.findByPassportNumber(passportNumber)
                        .map(clientMapper::toDto)
                        .orElseThrow(() -> new ServiceException(ClientExceptionCode.CLIENT_NOT_FOUNT_BY_PASSPORT_NUMBER.getMessage() + passportNumber));
    }

    @Transactional
    public void updateClient(ClientDto updatedClientDto) {
        getClientById(updatedClientDto.getId());
        clientRepository.save(clientMapper.toEntity(updatedClientDto));
    }

    @Transactional
    public void deleteClientById(int clientId) {
        clientRepository.deleteById(clientId);
    }
}
