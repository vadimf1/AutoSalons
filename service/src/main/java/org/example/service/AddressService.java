package org.example.service;

import org.example.dto.AddressDto;

import java.util.List;

public interface AddressService {
    void addAddress(AddressDto addressDto);
    List<AddressDto> getAllAddresses();
    AddressDto getAddressById(int id);
    void updateAddress(AddressDto addressDto);
    void deleteAddressById(int id);
}
