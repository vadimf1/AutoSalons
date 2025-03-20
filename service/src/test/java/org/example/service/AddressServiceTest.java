package org.example.service;

import org.example.dto.request.AddressRequestDto;
import org.example.dto.response.AddressResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.AddressMapper;
import org.example.model.Address;
import org.example.repository.AddressRepository;
import org.example.service.impl.AddressServiceImpl;
import org.example.util.error.AddressExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressRequestDto addressRequestDto;
    private AddressResponseDto addressResponseDto;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .id(1)
                .country("Country")
                .city("City")
                .state("State")
                .street("Street")
                .postalCode("Postal Code")
                .build();

        addressRequestDto = AddressRequestDto.builder()
                .country("USA")
                .city("New York")
                .state("NY")
                .street("5th Avenue")
                .postalCode("10001")
                .build();

        addressResponseDto = AddressResponseDto.builder()
                .id(1)
                .country("USA")
                .city("New York")
                .state("NY")
                .street("5th Avenue")
                .postalCode("10001")
                .build();
    }

    @Test
    void addAddress_ShouldSaveAddress() {
        when(addressMapper.toEntity(addressRequestDto)).thenReturn(address);

        addressService.addAddress(addressRequestDto);

        verify(addressRepository, times(1)).save(address);
    }

    @Test
    void getAllAddresses_ShouldReturnListOfAddressResponseDtos() {
        when(addressRepository.findAll()).thenReturn(List.of(address));
        when(addressMapper.toDto(address)).thenReturn(addressResponseDto);

        List<AddressResponseDto> result = addressService.getAllAddresses();

        assertEquals(1, result.size());
        assertEquals(addressResponseDto, result.get(0));
    }

    @Test
    void getAddressById_ShouldReturnAddressResponseDto_WhenAddressExists() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(addressMapper.toDto(address)).thenReturn(addressResponseDto);

        AddressResponseDto result = addressService.getAddressById(1);

        assertEquals(addressResponseDto, result);
    }

    @Test
    void getAddressById_ShouldThrowException_WhenAddressNotFound() {
        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                addressService.getAddressById(1));

        assertEquals(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + 1, exception.getMessage());
    }

    @Test
    void updateAddress_ShouldUpdateAddress() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        doNothing().when(addressMapper).updateEntityFromDto(addressRequestDto, address);

        addressService.updateAddress(1, addressRequestDto);

        verify(addressRepository, times(1)).save(address);
    }

    @Test
    void updateAddress_ShouldThrowException_WhenAddressNotFound() {
        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                addressService.updateAddress(1, addressRequestDto));

        assertEquals(AddressExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage() + 1, exception.getMessage());
    }

    @Test
    void deleteAddressById_ShouldDeleteAddress() {
        addressService.deleteAddressById(1);

        verify(addressRepository, times(1)).deleteById(1);
    }
}
