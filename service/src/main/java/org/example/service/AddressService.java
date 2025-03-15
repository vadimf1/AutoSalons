package org.example.service;

import org.example.dto.request.AddressRequestDto;
import org.example.dto.response.AddressResponseDto;

import java.util.List;

public interface AddressService {
    void addAddress(AddressRequestDto addressDto);
    List<AddressResponseDto> getAllAddresses();
    AddressResponseDto getAddressById(int id);
    void updateAddress(int id, AddressRequestDto addressDto);
    void deleteAddressById(int id);
}
