package org.example.controller;

import org.example.dto.request.DealerCarRequestDto;
import org.example.dto.response.DealerCarResponseDto;
import org.example.exception.ServiceException;
import org.example.service.DealerCarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealerCarControllerTest {

    @Mock
    private DealerCarService dealerCarService;

    @InjectMocks
    private DealerCarController dealerCarController;

    private DealerCarRequestDto dealerCarRequestDto;
    private DealerCarResponseDto dealerCarResponseDto;
    private List<DealerCarResponseDto> dealerCars;

    @BeforeEach
    void setUp() {
        dealerCarRequestDto = DealerCarRequestDto.builder()
                .dealerId(1)
                .carId(2)
                .price(BigDecimal.valueOf(30000.0))
                .build();

        dealerCarResponseDto = DealerCarResponseDto.builder()
                .build();

        dealerCars = Arrays.asList(dealerCarResponseDto);
    }

    @Test
    void getAllDealerCars_ShouldReturnListOfDealerCars() {
        when(dealerCarService.getAllDealerCars()).thenReturn(dealerCars);

        ResponseEntity<List<DealerCarResponseDto>> response = dealerCarController.getAllDealerCars();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(dealerCars, response.getBody());
        verify(dealerCarService).getAllDealerCars();
    }

    @Test
    void getDealerCarById_ShouldReturnDealerCar() {
        when(dealerCarService.getDealerCarById(anyInt())).thenReturn(dealerCarResponseDto);

        ResponseEntity<DealerCarResponseDto> response = dealerCarController.getDealerCarById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(dealerCarResponseDto, response.getBody());
        verify(dealerCarService).getDealerCarById(1);
    }

    @Test
    void getDealerCarsByDealerId_ShouldReturnListOfDealerCars() {
        when(dealerCarService.getDealerCarsByDealerId(anyInt())).thenReturn(dealerCars);

        ResponseEntity<List<DealerCarResponseDto>> response = dealerCarController.getDealerCarsByDealerId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(dealerCars, response.getBody());
        verify(dealerCarService).getDealerCarsByDealerId(1);
    }

    @Test
    void getDealerCarsByCarId_ShouldReturnListOfDealerCars() {
        when(dealerCarService.getDealersCarByCarId(anyInt())).thenReturn(dealerCars);

        ResponseEntity<List<DealerCarResponseDto>> response = dealerCarController.getDealersCarByCarId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(dealerCars, response.getBody());
        verify(dealerCarService).getDealersCarByCarId(1);
    }

    @Test
    void createDealerCar_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = dealerCarController.addDealerCar(dealerCarRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dealer car added successfully", response.getBody());
        verify(dealerCarService).addDealerCar(dealerCarRequestDto);
    }

    @Test
    void updateDealerCar_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = dealerCarController.updateDealerCar(1, dealerCarRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dealer car updated successfully", response.getBody());
        verify(dealerCarService).updateDealerCar(1, dealerCarRequestDto);
    }

    @Test
    void deleteDealerCar_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = dealerCarController.deleteDealerCar(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dealer car deleted successfully", response.getBody());
        verify(dealerCarService).deleteDealerCarById(1);
    }

    @Test
    void getAllDealerCars_ShouldHandleServiceException() {
        when(dealerCarService.getAllDealerCars()).thenThrow(new ServiceException("Failed to get dealer cars"));

        assertThrows(ServiceException.class, () -> dealerCarController.getAllDealerCars());
        verify(dealerCarService).getAllDealerCars();
    }

    @Test
    void getDealerCarById_ShouldHandleServiceException() {
        when(dealerCarService.getDealerCarById(anyInt())).thenThrow(new ServiceException("Dealer car not found"));

        assertThrows(ServiceException.class, () -> dealerCarController.getDealerCarById(1));
        verify(dealerCarService).getDealerCarById(1);
    }

    @Test
    void getDealerCarsByDealerId_ShouldHandleServiceException() {
        when(dealerCarService.getDealerCarsByDealerId(anyInt())).thenThrow(new ServiceException("Failed to get dealer cars by dealer"));

        assertThrows(ServiceException.class, () -> dealerCarController.getDealerCarsByDealerId(1));
        verify(dealerCarService).getDealerCarsByDealerId(1);
    }

    @Test
    void getDealerCarsByCarId_ShouldHandleServiceException() {
        when(dealerCarService.getDealersCarByCarId(anyInt())).thenThrow(new ServiceException("Failed to get dealer cars by car"));

        assertThrows(ServiceException.class, () -> dealerCarController.getDealersCarByCarId(1));
        verify(dealerCarService).getDealersCarByCarId(1);
    }

    @Test
    void createDealerCar_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create dealer car")).when(dealerCarService).addDealerCar(any(DealerCarRequestDto.class));

        assertThrows(ServiceException.class, () -> dealerCarController.addDealerCar(dealerCarRequestDto));
        verify(dealerCarService).addDealerCar(dealerCarRequestDto);
    }

    @Test
    void updateDealerCar_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update dealer car")).when(dealerCarService).updateDealerCar(anyInt(), any(DealerCarRequestDto.class));

        assertThrows(ServiceException.class, () -> dealerCarController.updateDealerCar(1, dealerCarRequestDto));
        verify(dealerCarService).updateDealerCar(1, dealerCarRequestDto);
    }

    @Test
    void deleteDealerCar_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete dealer car")).when(dealerCarService).deleteDealerCarById(anyInt());

        assertThrows(ServiceException.class, () -> dealerCarController.deleteDealerCar(1));
        verify(dealerCarService).deleteDealerCarById(1);
    }
} 