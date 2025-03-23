package org.example.controller;

import org.example.dto.request.AddCarDto;
import org.example.dto.request.CarFilterRequest;
import org.example.dto.request.UpdateCarDto;
import org.example.dto.response.CarResponseDto;
import org.example.exception.ServiceException;
import org.example.service.CarService;
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
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private AddCarDto addCarDto;
    private UpdateCarDto updateCarDto;
    private CarResponseDto carResponseDto;
    private List<CarResponseDto> cars;
    private CarFilterRequest carFilterRequest;

    @BeforeEach
    void setUp() {
        addCarDto = AddCarDto.builder()
                .make("Toyota")
                .model("Camry")
                .year(2023)
                .vin("1234567890")
                .build();

        updateCarDto = UpdateCarDto.builder()
                .make("Toyota")
                .model("Camry")
                .year(2024)
                .vin("1234567890")
                .build();

        carResponseDto = CarResponseDto.builder()
                .id(1)
                .make("Toyota")
                .model("Camry")
                .year(2023)
                .vin("1234567890")
                .build();

        cars = Arrays.asList(carResponseDto);

        carFilterRequest = new CarFilterRequest();
        carFilterRequest.setMake("Toyota");
        carFilterRequest.setModel("Camry");
        carFilterRequest.setYear(2023);
    }


    @Test
    void getAllCars_ShouldReturnListOfCars() {
        when(carService.getAllCars()).thenReturn(cars);

        ResponseEntity<List<CarResponseDto>> response = carController.getAllCars();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(cars, response.getBody());
        verify(carService).getAllCars();
    }

    @Test
    void getCarById_ShouldReturnCar() {
        when(carService.getCarById(anyInt())).thenReturn(carResponseDto);

        ResponseEntity<CarResponseDto> response = carController.getCarById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(carResponseDto, response.getBody());
        verify(carService).getCarById(1);
    }

    @Test
    void getFilteredCars_ShouldReturnFilteredCars() {
        when(carService.getFilteredCars(any(CarFilterRequest.class))).thenReturn(cars);

        ResponseEntity<List<CarResponseDto>> response = carController.getFilteredCars(carFilterRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(cars, response.getBody());
        verify(carService).getFilteredCars(carFilterRequest);
    }

    @Test
    void addCar_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = carController.addCar(addCarDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Car added successfully", response.getBody());
        verify(carService).addCar(addCarDto);
    }

    @Test
    void updateCar_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = carController.updateCar(1, updateCarDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Car updated successfully", response.getBody());
        verify(carService).updateCar(1, updateCarDto);
    }

    @Test
    void deleteCar_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = carController.deleteCar(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Car deleted successfully", response.getBody());
        verify(carService).deleteCarById(1);
    }

    @Test
    void getAllCars_ShouldHandleServiceException() {
        when(carService.getAllCars()).thenThrow(new ServiceException("Failed to get cars"));

        assertThrows(ServiceException.class, () -> carController.getAllCars());
        verify(carService).getAllCars();
    }

    @Test
    void getCarById_ShouldHandleServiceException() {
        when(carService.getCarById(anyInt()))
                .thenThrow(new ServiceException("Car not found"));

        assertThrows(ServiceException.class, () -> carController.getCarById(1));
        verify(carService).getCarById(1);
    }

    @Test
    void getFilteredCars_ShouldHandleServiceException() {
        when(carService.getFilteredCars(any(CarFilterRequest.class)))
                .thenThrow(new ServiceException("Failed to filter cars"));

        assertThrows(ServiceException.class, () -> carController.getFilteredCars(carFilterRequest));
        verify(carService).getFilteredCars(carFilterRequest);
    }

    @Test
    void addCar_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to add car")).when(carService).addCar(any(AddCarDto.class));

        assertThrows(ServiceException.class, () -> carController.addCar(addCarDto));
        verify(carService).addCar(addCarDto);
    }

    @Test
    void updateCar_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update car")).when(carService).updateCar(anyInt(), any(UpdateCarDto.class));

        assertThrows(ServiceException.class, () -> carController.updateCar(1, updateCarDto));
        verify(carService).updateCar(1, updateCarDto);
    }

    @Test
    void deleteCar_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete car")).when(carService).deleteCarById(anyInt());

        assertThrows(ServiceException.class, () -> carController.deleteCar(1));
        verify(carService).deleteCarById(1);
    }
} 