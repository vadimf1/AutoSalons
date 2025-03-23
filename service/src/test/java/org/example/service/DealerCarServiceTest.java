package org.example.service;

import org.example.dto.request.DealerCarRequestDto;
import org.example.dto.response.DealerCarResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.DealerCarMapper;
import org.example.model.DealerCar;
import org.example.model.Dealer;
import org.example.model.Car;
import org.example.repository.DealerCarRepository;
import org.example.repository.DealerRepository;
import org.example.repository.CarRepository;
import org.example.service.impl.DealerCarServiceImpl;
import org.example.util.error.CarExceptionCode;
import org.example.util.error.DealerCarExceptionCode;
import org.example.util.error.DealerExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealerCarServiceTest {

    @Mock
    private DealerCarRepository dealerCarRepository;
    @Mock
    private DealerRepository dealerRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private DealerCarMapper dealerCarMapper;

    @InjectMocks
    private DealerCarServiceImpl dealerCarService;

    private DealerCarRequestDto dealerCarRequestDto;
    private DealerCar dealerCar;
    private DealerCarResponseDto dealerCarResponseDto;
    private Dealer dealer;
    private Car car;

    @BeforeEach
    void setUp() {
        dealerCarRequestDto = DealerCarRequestDto.builder()
                .dealerId(1)
                .carId(1)
                .price(new BigDecimal("50000"))
                .build();

        dealer = new Dealer();
        car = new Car();
        dealerCar = new DealerCar();
        
        dealerCarResponseDto = DealerCarResponseDto.builder()
                .id(1)
                .price(new BigDecimal("50000"))
                .build();
    }

    @Test
    void addDealerCar_ShouldSaveDealerCar() {
        when(dealerCarMapper.toEntity(dealerCarRequestDto)).thenReturn(dealerCar);
        when(dealerRepository.findById(dealerCarRequestDto.getDealerId())).thenReturn(Optional.of(dealer));
        when(carRepository.findById(dealerCarRequestDto.getCarId())).thenReturn(Optional.of(car));

        dealerCarService.addDealerCar(dealerCarRequestDto);

        verify(dealerCarRepository).save(dealerCar);
    }

    @Test
    void getAllDealerCars_ShouldReturnListOfDealerCars() {
        when(dealerCarRepository.findAll()).thenReturn(Collections.singletonList(dealerCar));
        when(dealerCarMapper.toDto(dealerCar)).thenReturn(dealerCarResponseDto);

        var result = dealerCarService.getAllDealerCars();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dealerCarResponseDto, result.get(0));
    }

    @Test
    void getDealerCarById_ShouldReturnDealerCar() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.of(dealerCar));
        when(dealerCarMapper.toDto(dealerCar)).thenReturn(dealerCarResponseDto);

        var result = dealerCarService.getDealerCarById(1);

        assertNotNull(result);
        assertEquals(dealerCarResponseDto, result);
    }

    @Test
    void updateDealerCar_ShouldUpdateDealerCar() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.of(dealerCar));
        when(dealerRepository.findById(dealerCarRequestDto.getDealerId())).thenReturn(Optional.of(dealer));
        when(carRepository.findById(dealerCarRequestDto.getCarId())).thenReturn(Optional.of(car));
        doNothing().when(dealerCarMapper).updateEntityFromDto(dealerCarRequestDto, dealerCar);

        dealerCarService.updateDealerCar(1, dealerCarRequestDto);

        verify(dealerCarRepository).save(dealerCar);
    }

    @Test
    void deleteDealerCarById_ShouldDeleteDealerCar() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.of(dealerCar));

        dealerCarService.deleteDealerCarById(1);

        verify(dealerCarRepository).deleteById(1);
    }

    @Test
    void addDealerCar_ShouldThrowException_WhenDealerNotFound() {
        when(dealerCarMapper.toEntity(dealerCarRequestDto)).thenReturn(dealerCar);
        when(dealerRepository.findById(dealerCarRequestDto.getDealerId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerCarService.addDealerCar(dealerCarRequestDto));

        assertEquals(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + dealerCarRequestDto.getDealerId(),
                exception.getMessage());
    }

    @Test
    void addDealerCar_ShouldThrowException_WhenCarNotFound() {
        when(dealerCarMapper.toEntity(dealerCarRequestDto)).thenReturn(dealerCar);
        when(dealerRepository.findById(dealerCarRequestDto.getDealerId())).thenReturn(Optional.of(dealer));
        when(carRepository.findById(dealerCarRequestDto.getCarId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerCarService.addDealerCar(dealerCarRequestDto));

        assertEquals(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + dealerCarRequestDto.getCarId(),
                exception.getMessage());
    }

    @Test
    void getDealerCarById_ShouldThrowException_WhenDealerCarNotFound() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerCarService.getDealerCarById(1));

        assertEquals(DealerCarExceptionCode.DEALER_CAR_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void updateDealerCar_ShouldThrowException_WhenDealerCarNotFound() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerCarService.updateDealerCar(1, dealerCarRequestDto));

        assertEquals(DealerCarExceptionCode.DEALER_CAR_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void updateDealerCar_ShouldThrowException_WhenDealerNotFound() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.of(dealerCar));
        when(dealerRepository.findById(dealerCarRequestDto.getDealerId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerCarService.updateDealerCar(1, dealerCarRequestDto));

        assertEquals(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + dealerCarRequestDto.getDealerId(),
                exception.getMessage());
    }

    @Test
    void updateDealerCar_ShouldThrowException_WhenCarNotFound() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.of(dealerCar));
        when(dealerRepository.findById(dealerCarRequestDto.getDealerId())).thenReturn(Optional.of(dealer));
        when(carRepository.findById(dealerCarRequestDto.getCarId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerCarService.updateDealerCar(1, dealerCarRequestDto));

        assertEquals(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + dealerCarRequestDto.getCarId(),
                exception.getMessage());
    }

    @Test
    void deleteDealerCarById_ShouldThrowException_WhenDealerCarNotFound() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerCarService.deleteDealerCarById(1));

        assertEquals(DealerCarExceptionCode.DEALER_CAR_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void getDealersCarByCarId_ShouldThrowException_WhenCarNotFound() {
        when(carRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerCarService.getDealersCarByCarId(1));

        assertEquals(CarExceptionCode.ID_FIELD_NOT_NULL.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void getDealersCarsByDealerId_ShouldThrowException_WhenDealerNotFound() {
        when(dealerRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerCarService.getDealerCarsByDealerId(1));

        assertEquals(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + 1,
                exception.getMessage());
    }
}
