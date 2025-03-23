package org.example.controller;

import org.example.dto.request.AddressRequestDto;
import org.example.dto.response.AddressResponseDto;
import org.example.exception.ServiceException;
import org.example.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    private AddressRequestDto addressRequestDto;
    private AddressResponseDto addressResponseDto;
    private List<AddressResponseDto> addresses;

    @BeforeEach
    void setUp() {
        addressRequestDto = AddressRequestDto.builder()
                .street("Test Street")
                .city("Test City")
                .state("Test State")
                .country("Test Country")
                .postalCode("12345")
                .build();

        addressResponseDto = AddressResponseDto.builder()
                .id(1)
                .street("Test Street")
                .city("Test City")
                .state("Test State")
                .country("Test Country")
                .postalCode("12345")
                .build();

        addresses = Arrays.asList(addressResponseDto);
    }

    @Test
    void getAllAddresses_ShouldReturnListOfAddresses() {
        when(addressService.getAllAddresses()).thenReturn(addresses);

        ResponseEntity<List<AddressResponseDto>> response = addressController.getAllAddresses();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(addresses, response.getBody());
        verify(addressService).getAllAddresses();
    }

    @Test
    void getAddressById_ShouldReturnAddress() {
        when(addressService.getAddressById(anyInt())).thenReturn(addressResponseDto);

        ResponseEntity<AddressResponseDto> response = addressController.getAddressById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(addressResponseDto, response.getBody());
        verify(addressService).getAddressById(1);
    }

    @Test
    void createAddress_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = addressController.addAddress(addressRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Address added successfully", response.getBody());
        verify(addressService).addAddress(addressRequestDto);
    }

    @Test
    void updateAddress_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = addressController.updateAddress(1, addressRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Address updated successfully", response.getBody());
        verify(addressService).updateAddress(1, addressRequestDto);
    }

    @Test
    void deleteAddress_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = addressController.deleteAddress(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Address deleted successfully", response.getBody());
        verify(addressService).deleteAddressById(1);
    }

    @Test
    void getAllAddresses_ShouldHandleServiceException() {
        when(addressService.getAllAddresses()).thenThrow(new ServiceException("Failed to get addresses"));

        assertThrows(ServiceException.class, () -> addressController.getAllAddresses());
        verify(addressService).getAllAddresses();
    }

    @Test
    void getAddressById_ShouldHandleServiceException() {
        when(addressService.getAddressById(anyInt())).thenThrow(new ServiceException("Address not found"));

        assertThrows(ServiceException.class, () -> addressController.getAddressById(1));
        verify(addressService).getAddressById(1);
    }

    @Test
    void createAddress_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create address")).when(addressService).addAddress(any(AddressRequestDto.class));

        assertThrows(ServiceException.class, () -> addressController.addAddress(addressRequestDto));
        verify(addressService).addAddress(addressRequestDto);
    }

    @Test
    void updateAddress_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update address")).when(addressService).updateAddress(anyInt(), any(AddressRequestDto.class));

        assertThrows(ServiceException.class, () -> addressController.updateAddress(1, addressRequestDto));
        verify(addressService).updateAddress(1, addressRequestDto);
    }

    @Test
    void deleteAddress_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete address")).when(addressService).deleteAddressById(anyInt());

        assertThrows(ServiceException.class, () -> addressController.deleteAddress(1));
        verify(addressService).deleteAddressById(1);
    }
} 