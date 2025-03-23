package org.example.controller;

import org.example.dto.request.TestDriveRequestDto;
import org.example.dto.response.TestDriveResponseDto;
import org.example.exception.ServiceException;
import org.example.service.TestDriveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestDriveControllerTest {

    @Mock
    private TestDriveService testDriveService;

    @InjectMocks
    private TestDriveController testDriveController;

    private TestDriveRequestDto testDriveRequestDto;
    private TestDriveResponseDto testDriveResponseDto;
    private List<TestDriveResponseDto> testDrives;

    @BeforeEach
    void setUp() {
        testDriveRequestDto = TestDriveRequestDto.builder()
                .autoSalonId(1)
                .clientId(2)
                .carId(3)
                .testDriveDate(LocalDate.now())
                .build();

        testDriveResponseDto = TestDriveResponseDto.builder()
                .build();

        testDrives = Arrays.asList(testDriveResponseDto);
    }

    @Test
    void getAllTestDrives_ShouldReturnListOfTestDrives() {
        when(testDriveService.getAllTestDrives()).thenReturn(testDrives);

        ResponseEntity<List<TestDriveResponseDto>> response = testDriveController.getAllTestDrives();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testDrives, response.getBody());
        verify(testDriveService).getAllTestDrives();
    }

    @Test
    void getTestDrivesByClient_ShouldReturnListOfTestDrives() {
        when(testDriveService.getTestDrivesByClientId(anyInt())).thenReturn(testDrives);

        ResponseEntity<List<TestDriveResponseDto>> response = testDriveController.getTestDrivesByClient(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testDrives, response.getBody());
        verify(testDriveService).getTestDrivesByClientId(1);
    }

    @Test
    void getTestDrivesByDate_ShouldReturnListOfTestDrives() {
        LocalDate date = LocalDate.now();
        when(testDriveService.getTestDrivesByDate(any(LocalDate.class))).thenReturn(testDrives);

        ResponseEntity<List<TestDriveResponseDto>> response = testDriveController.getTestDrivesByDate(date);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testDrives, response.getBody());
        verify(testDriveService).getTestDrivesByDate(date);
    }

    @Test
    void getTestDriveById_ShouldReturnTestDrive() {
        when(testDriveService.getTestDriveById(anyInt())).thenReturn(testDriveResponseDto);

        ResponseEntity<TestDriveResponseDto> response = testDriveController.getTestDriveById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testDriveResponseDto, response.getBody());
        verify(testDriveService).getTestDriveById(1);
    }

    @Test
    void createTestDrive_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = testDriveController.createTestDrive(testDriveRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test drive added successfully", response.getBody());
        verify(testDriveService).addTestDrive(testDriveRequestDto);
    }

    @Test
    void updateTestDrive_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = testDriveController.updateTestDrive(1, testDriveRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test drive updated successfully", response.getBody());
        verify(testDriveService).updateTestDrive(1, testDriveRequestDto);
    }

    @Test
    void getAllTestDrives_ShouldHandleServiceException() {
        when(testDriveService.getAllTestDrives()).thenThrow(new ServiceException("Failed to get test drives"));

        assertThrows(ServiceException.class, () -> testDriveController.getAllTestDrives());
        verify(testDriveService).getAllTestDrives();
    }

    @Test
    void getTestDrivesByClient_ShouldHandleServiceException() {
        when(testDriveService.getTestDrivesByClientId(anyInt()))
                .thenThrow(new ServiceException("Failed to get test drives by client"));

        assertThrows(ServiceException.class, () -> testDriveController.getTestDrivesByClient(1));
        verify(testDriveService).getTestDrivesByClientId(1);
    }

    @Test
    void getTestDrivesByDate_ShouldHandleServiceException() {
        when(testDriveService.getTestDrivesByDate(any(LocalDate.class)))
                .thenThrow(new ServiceException("Failed to get test drives by date"));

        assertThrows(ServiceException.class, () -> testDriveController.getTestDrivesByDate(LocalDate.now()));
        verify(testDriveService).getTestDrivesByDate(any(LocalDate.class));
    }

    @Test
    void getTestDriveById_ShouldHandleServiceException() {
        when(testDriveService.getTestDriveById(anyInt()))
                .thenThrow(new ServiceException("Test drive not found"));

        assertThrows(ServiceException.class, () -> testDriveController.getTestDriveById(1));
        verify(testDriveService).getTestDriveById(1);
    }

    @Test
    void createTestDrive_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create test drive")).when(testDriveService).addTestDrive(any(TestDriveRequestDto.class));

        assertThrows(ServiceException.class, () -> testDriveController.createTestDrive(testDriveRequestDto));
        verify(testDriveService).addTestDrive(testDriveRequestDto);
    }

    @Test
    void updateTestDrive_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update test drive")).when(testDriveService).updateTestDrive(anyInt(), any(TestDriveRequestDto.class));

        assertThrows(ServiceException.class, () -> testDriveController.updateTestDrive(1, testDriveRequestDto));
        verify(testDriveService).updateTestDrive(1, testDriveRequestDto);
    }
} 