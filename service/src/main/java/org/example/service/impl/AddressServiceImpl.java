package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.AddressRequestDto;
import org.example.dto.response.AddressResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.AddressMapper;
import org.example.model.Address;
import org.example.repository.AddressRepository;
import org.example.service.AddressService;
import org.example.util.error.AddressExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public void addAddress(AddressRequestDto addressDto) {
        addressRepository.save(addressMapper.toEntity(addressDto));
    }

    public List<AddressResponseDto> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }

    public AddressResponseDto getAddressById(int id) {
        return addressRepository.findById(id)
                .map(addressMapper::toDto)
                .orElseThrow(() -> new ServiceException(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + id));
    }

    public void updateAddress(int id, AddressRequestDto addressDto) {
        Address address = addressRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(AddressExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage() + id));

        addressMapper.updateEntityFromDto(addressDto, address);

        addressRepository.save(address);
    }

    public void deleteAddressById(int id) {
        addressRepository.deleteById(id);
    }
}
