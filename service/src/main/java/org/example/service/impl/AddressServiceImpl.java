package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.AddressDto;
import org.example.exception.ServiceException;
import org.example.mapper.AddressMapper;
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

    public void addAddress(AddressDto addressDto) {
        if (addressDto.getId() != null) {
            throw new ServiceException(AddressExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }
        addressRepository.save(addressMapper.toEntity(addressDto));
    }

    public List<AddressDto> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }

    public AddressDto getAddressById(int id) {
        return addressRepository.findById(id)
                .map(addressMapper::toDto)
                .orElseThrow(() -> new ServiceException(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + id));
    }

    public void updateAddress(AddressDto addressDto) {
        getAddressById(addressDto.getId());
        addressRepository.save(addressMapper.toEntity(addressDto));
    }

    public void deleteAddressById(int id) {
        addressRepository.deleteById(id);
    }
}
