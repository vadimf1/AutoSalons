package org.example.controller;

import org.example.dto.request.AutoSalonRequestDto;
import org.example.dto.response.AutoSalonResponseDto;
import org.example.exception.ServiceException;
import org.example.service.AutoSalonService;
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
class AutoSalonControllerTest {

    @Mock
    private AutoSalonService autoSalonService;

    @InjectMocks
    private AutoSalonController autoSalonController;

    private AutoSalonRequestDto autoSalonRequestDto;
    private AutoSalonResponseDto autoSalonResponseDto;
    private List<AutoSalonResponseDto> autoSalons;

    @BeforeEach
    void setUp() {
        autoSalonRequestDto = AutoSalonRequestDto.builder()
                .name("AutoSalon Plus")
                .addressId(1)
                .contactIds(List.of(1, 2))
                .build();

        autoSalonResponseDto = AutoSalonResponseDto.builder()
                .build();

        autoSalons = Arrays.asList(autoSalonResponseDto);
    }

    @Test
    void getAllAutoSalons_ShouldReturnListOfAutoSalons() {
        when(autoSalonService.getAllAutoSalons()).thenReturn(autoSalons);

        ResponseEntity<List<AutoSalonResponseDto>> response = autoSalonController.getAllAutoSalons();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(autoSalons, response.getBody());
        verify(autoSalonService).getAllAutoSalons();
    }

    @Test
    void getAutoSalonById_ShouldReturnAutoSalon() {
        when(autoSalonService.getAutoSalonById(anyInt())).thenReturn(autoSalonResponseDto);

        ResponseEntity<AutoSalonResponseDto> response = autoSalonController.getAutoSalonById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(autoSalonResponseDto, response.getBody());
        verify(autoSalonService).getAutoSalonById(1);
    }

    @Test
    void createAutoSalon_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = autoSalonController.addAutoSalon(autoSalonRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Auto salon added successfully", response.getBody());
        verify(autoSalonService).addAutoSalon(autoSalonRequestDto);
    }

    @Test
    void updateAutoSalon_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = autoSalonController.updateAutoSalon(1, autoSalonRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Auto salon updated successfully", response.getBody());
        verify(autoSalonService).updateAutoSalon(1, autoSalonRequestDto);
    }

    @Test
    void deleteAutoSalon_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = autoSalonController.deleteAutoSalon(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Auto salon deleted successfully", response.getBody());
        verify(autoSalonService).deleteAutoSalonById(1);
    }

    @Test
    void getAllAutoSalons_ShouldHandleServiceException() {
        when(autoSalonService.getAllAutoSalons()).thenThrow(new ServiceException("Failed to get auto salons"));

        assertThrows(ServiceException.class, () -> autoSalonController.getAllAutoSalons());
        verify(autoSalonService).getAllAutoSalons();
    }

    @Test
    void getAutoSalonById_ShouldHandleServiceException() {
        when(autoSalonService.getAutoSalonById(anyInt())).thenThrow(new ServiceException("AutoSalon not found"));

        assertThrows(ServiceException.class, () -> autoSalonController.getAutoSalonById(1));
        verify(autoSalonService).getAutoSalonById(1);
    }

    @Test
    void createAutoSalon_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create auto salon")).when(autoSalonService).addAutoSalon(any(AutoSalonRequestDto.class));

        assertThrows(ServiceException.class, () -> autoSalonController.addAutoSalon(autoSalonRequestDto));
        verify(autoSalonService).addAutoSalon(autoSalonRequestDto);
    }

    @Test
    void updateAutoSalon_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update auto salon")).when(autoSalonService).updateAutoSalon(anyInt(), any(AutoSalonRequestDto.class));

        assertThrows(ServiceException.class, () -> autoSalonController.updateAutoSalon(1, autoSalonRequestDto));
        verify(autoSalonService).updateAutoSalon(1, autoSalonRequestDto);
    }

    @Test
    void deleteAutoSalon_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete auto salon")).when(autoSalonService).deleteAutoSalonById(anyInt());

        assertThrows(ServiceException.class, () -> autoSalonController.deleteAutoSalon(1));
        verify(autoSalonService).deleteAutoSalonById(1);
    }
}