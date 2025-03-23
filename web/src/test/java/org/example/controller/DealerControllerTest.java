package org.example.controller;

import org.example.dto.request.DealerRequestDto;
import org.example.dto.response.DealerResponseDto;
import org.example.exception.ServiceException;
import org.example.service.DealerService;
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
class DealerControllerTest {

    @Mock
    private DealerService dealerService;

    @InjectMocks
    private DealerController dealerController;

    private DealerRequestDto dealerRequestDto;
    private DealerResponseDto dealerResponseDto;
    private List<DealerResponseDto> dealers;

    @BeforeEach
    void setUp() {
        dealerRequestDto = DealerRequestDto.builder()
                .name("Test Dealer")
                .addressId(1)
                .contactIds(List.of(1, 2))
                .build();

        dealerResponseDto = DealerResponseDto.builder()
                .build();

        dealers = Arrays.asList(dealerResponseDto);
    }

    @Test
    void getAllDealers_ShouldReturnListOfDealers() {
        when(dealerService.getAllDealers()).thenReturn(dealers);

        ResponseEntity<List<DealerResponseDto>> response = dealerController.getAllDealers();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(dealers, response.getBody());
        verify(dealerService).getAllDealers();
    }

    @Test
    void getDealerById_ShouldReturnDealer() {
        when(dealerService.getDealerById(anyInt())).thenReturn(dealerResponseDto);

        ResponseEntity<DealerResponseDto> response = dealerController.getDealerById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(dealerResponseDto, response.getBody());
        verify(dealerService).getDealerById(1);
    }

    @Test
    void createDealer_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = dealerController.addDealer(dealerRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dealer added successfully", response.getBody());
        verify(dealerService).addDealer(dealerRequestDto);
    }

    @Test
    void updateDealer_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = dealerController.updateDealer(1, dealerRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dealer updated successfully", response.getBody());
        verify(dealerService).updateDealer(1, dealerRequestDto);
    }

    @Test
    void deleteDealer_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = dealerController.deleteDealer(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dealer deleted successfully", response.getBody());
        verify(dealerService).deleteDealerById(1);
    }

    @Test
    void getAllDealers_ShouldHandleServiceException() {
        when(dealerService.getAllDealers()).thenThrow(new ServiceException("Failed to get dealers"));

        assertThrows(ServiceException.class, () -> dealerController.getAllDealers());
        verify(dealerService).getAllDealers();
    }

    @Test
    void getDealerById_ShouldHandleServiceException() {
        when(dealerService.getDealerById(anyInt())).thenThrow(new ServiceException("Dealer not found"));

        assertThrows(ServiceException.class, () -> dealerController.getDealerById(1));
        verify(dealerService).getDealerById(1);
    }

    @Test
    void createDealer_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create dealer")).when(dealerService).addDealer(any(DealerRequestDto.class));

        assertThrows(ServiceException.class, () -> dealerController.addDealer(dealerRequestDto));
        verify(dealerService).addDealer(dealerRequestDto);
    }

    @Test
    void updateDealer_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update dealer")).when(dealerService).updateDealer(anyInt(), any(DealerRequestDto.class));

        assertThrows(ServiceException.class, () -> dealerController.updateDealer(1, dealerRequestDto));
        verify(dealerService).updateDealer(1, dealerRequestDto);
    }

    @Test
    void deleteDealer_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete dealer")).when(dealerService).deleteDealerById(anyInt());

        assertThrows(ServiceException.class, () -> dealerController.deleteDealer(1));
        verify(dealerService).deleteDealerById(1);
    }
} 