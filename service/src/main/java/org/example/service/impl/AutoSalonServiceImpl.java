package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.AutoSalonFilterRequest;
import org.example.dto.request.AutoSalonRequestDto;
import org.example.dto.response.AutoSalonResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.AutoSalonMapper;
import org.example.model.Address;
import org.example.model.AutoSalon;
import org.example.model.Contact;
import org.example.repository.AddressRepository;
import org.example.repository.AutoSalonRepository;
import org.example.repository.ContactRepository;
import org.example.repository.specification.AutoSalonSpecification;
import org.example.service.AutoSalonService;
import org.example.util.error.AddressExceptionCode;
import org.example.util.error.AutoSalonExceptionCode;
import org.example.util.error.ContactExceptionCode;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AutoSalonServiceImpl implements AutoSalonService {
    private final AutoSalonRepository autoSalonRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final AutoSalonMapper autoSalonMapper;

    public void addAutoSalon(AutoSalonRequestDto autoSalonDto) {
        AutoSalon autoSalon = autoSalonMapper.toEntity(autoSalonDto);

        addRelationsToAutoSalon(autoSalon, autoSalonDto);

        autoSalonRepository.save(autoSalon);
    }

    private void addRelationsToAutoSalon(AutoSalon autoSalon, AutoSalonRequestDto autoSalonDto) {
        Address address = addressRepository.findById(autoSalonDto.getAddressId())
                .orElseThrow(() -> new ServiceException(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + autoSalonDto.getAddressId()));

        autoSalon.setAddress(address);

        autoSalon.setContacts(new HashSet<>());
        for (Integer contactId : autoSalonDto.getContactIds()) {
            if (contactId == null) {
                throw new ServiceException(ContactExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
            }

            Contact contact = contactRepository.findById(contactId)
                    .orElseThrow(() -> new ServiceException(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + contactId));

            autoSalon.addContact(contact);
        }
    }

    public List<AutoSalonResponseDto> getAllAutoSalons() {
        return autoSalonRepository.findAll()
                .stream()
                .map(autoSalonMapper::toDto)
                .toList();
    }

    public AutoSalonResponseDto getAutoSalonById(int id) {
        return autoSalonRepository.findById(id)
                .map(autoSalonMapper::toDto)
                .orElseThrow(() -> new ServiceException(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + id));
    }

    public void updateAutoSalon(int id, AutoSalonRequestDto autoSalonDto) {
        AutoSalon autoSalon = autoSalonRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + id));

        autoSalonMapper.updateEntityFromDto(autoSalonDto, autoSalon);
        addRelationsToAutoSalon(autoSalon, autoSalonDto);

        autoSalonRepository.save(autoSalon);
    }

    public void deleteAutoSalonById(int id) {
        autoSalonRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + id));
        autoSalonRepository.deleteById(id);
    }

    @Override
    public List<AutoSalonResponseDto> getFilteredAutoSalons(AutoSalonFilterRequest autoSalonFilterRequest) {
        return autoSalonRepository.findAll(AutoSalonSpecification.filter(
                        autoSalonFilterRequest.getCity(),
                        autoSalonFilterRequest.getName()
                ))
                .stream()
                .map(autoSalonMapper::toDto)
                .toList();
    }
}
