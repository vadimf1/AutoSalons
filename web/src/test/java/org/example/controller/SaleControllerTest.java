package org.example.controller;

import org.example.dto.request.SaleRequestDto;
import org.example.dto.response.SaleResponseDto;
import org.example.exception.ServiceException;
import org.example.service.SaleService;
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
class SaleControllerTest {

    @Mock
    private SaleService saleService;

    @InjectMocks
    private SaleController saleController;

    private SaleRequestDto saleRequestDto;
    private SaleResponseDto saleResponseDto;
    private List<SaleResponseDto> sales;

    @BeforeEach
    void setUp() {
        saleRequestDto = SaleRequestDto.builder()
                .autoSalonId(1)
                .clientId(2)
                .carId(3)
                .employeeId(4)
                .finalPrice(BigDecimal.valueOf(50000.0))
                .build();

        saleResponseDto = SaleResponseDto.builder()
                .build();

        sales = Arrays.asList(saleResponseDto);
    }

    @Test
    void getAllSales_ShouldReturnListOfSales() {
        when(saleService.getAllSales()).thenReturn(sales);

        ResponseEntity<List<SaleResponseDto>> response = saleController.getAllSales();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(sales, response.getBody());
        verify(saleService).getAllSales();
    }

    @Test
    void getSaleById_ShouldReturnSale() {
        when(saleService.getSaleById(anyInt())).thenReturn(saleResponseDto);

        ResponseEntity<SaleResponseDto> response = saleController.getSaleById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(saleResponseDto, response.getBody());
        verify(saleService).getSaleById(1);
    }

    @Test
    void getSaleByClientId_ShouldReturnListOfSales() {
        when(saleService.getSaleByClientId(anyInt())).thenReturn(sales);

        ResponseEntity<List<SaleResponseDto>> response = saleController.getSaleByClientId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(sales, response.getBody());
        verify(saleService).getSaleByClientId(1);
    }

    @Test
    void addSale_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = saleController.addSale(saleRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sale added successfully", response.getBody());
        verify(saleService).addSale(saleRequestDto);
    }

    @Test
    void updateSale_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = saleController.updateSale(1, saleRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sale updated successfully", response.getBody());
        verify(saleService).updateSale(1, saleRequestDto);
    }

    @Test
    void deleteSale_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = saleController.deleteSale(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sale deleted successfully", response.getBody());
        verify(saleService).deleteSaleById(1);
    }

    @Test
    void getAllSales_ShouldHandleServiceException() {
        when(saleService.getAllSales()).thenThrow(new ServiceException("Failed to get sales"));

        assertThrows(ServiceException.class, () -> saleController.getAllSales());
        verify(saleService).getAllSales();
    }

    @Test
    void getSaleById_ShouldHandleServiceException() {
        when(saleService.getSaleById(anyInt()))
                .thenThrow(new ServiceException("Sale not found"));

        assertThrows(ServiceException.class, () -> saleController.getSaleById(1));
        verify(saleService).getSaleById(1);
    }

    @Test
    void getSaleByClientId_ShouldHandleServiceException() {
        when(saleService.getSaleByClientId(anyInt()))
                .thenThrow(new ServiceException("Failed to get sales by client"));

        assertThrows(ServiceException.class, () -> saleController.getSaleByClientId(1));
        verify(saleService).getSaleByClientId(1);
    }

    @Test
    void addSale_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to add sale")).when(saleService).addSale(any(SaleRequestDto.class));

        assertThrows(ServiceException.class, () -> saleController.addSale(saleRequestDto));
        verify(saleService).addSale(saleRequestDto);
    }

    @Test
    void updateSale_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update sale")).when(saleService).updateSale(anyInt(), any(SaleRequestDto.class));

        assertThrows(ServiceException.class, () -> saleController.updateSale(1, saleRequestDto));
        verify(saleService).updateSale(1, saleRequestDto);
    }

    @Test
    void deleteSale_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete sale")).when(saleService).deleteSaleById(anyInt());

        assertThrows(ServiceException.class, () -> saleController.deleteSale(1));
        verify(saleService).deleteSaleById(1);
    }
} 