package org.example.service;

import org.example.dto.request.DealerCarRequestDto;
import org.example.dto.response.DealerCarResponseDto;
import org.example.mapper.DealerCarMapper;
import org.example.model.DealerCar;
import org.example.model.Dealer;
import org.example.model.Car;
import org.example.repository.DealerCarRepository;
import org.example.repository.DealerRepository;
import org.example.repository.CarRepository;
import org.example.service.impl.DealerCarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.*;

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
                .carId(2)
                .price(BigDecimal.valueOf(20000))
                .build();

        dealer = new Dealer();
        car = new Car();

        dealerCar = DealerCar.builder()
                .dealer(dealer)
                .car(car)
                .price(dealerCarRequestDto.getPrice())
                .build();

        dealerCarResponseDto = DealerCarResponseDto.builder()
                .id(1)
                .price(dealerCarRequestDto.getPrice())
                .build();
    }

    @Test
    void testAddDealerCar() {
        when(dealerCarMapper.toEntity(dealerCarRequestDto)).thenReturn(dealerCar);
        when(dealerRepository.findById(dealerCarRequestDto.getDealerId())).thenReturn(Optional.of(dealer));
        when(carRepository.findById(dealerCarRequestDto.getCarId())).thenReturn(Optional.of(car));
        when(dealerCarRepository.save(dealerCar)).thenReturn(dealerCar);

        dealerCarService.addDealerCar(dealerCarRequestDto);

        verify(dealerCarMapper).toEntity(dealerCarRequestDto);
        verify(dealerRepository).findById(dealerCarRequestDto.getDealerId());
        verify(carRepository).findById(dealerCarRequestDto.getCarId());
        verify(dealerCarRepository).save(dealerCar);
    }

    @Test
    void testGetAllDealerCars() {
        List<DealerCar> dealerCars = Collections.singletonList(dealerCar);
        when(dealerCarRepository.findAll()).thenReturn(dealerCars);
        when(dealerCarMapper.toDto(dealerCar)).thenReturn(dealerCarResponseDto);

        List<DealerCarResponseDto> result = dealerCarService.getAllDealerCars();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dealerCarResponseDto, result.get(0));
    }

    @Test
    void testGetDealerCarById() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.of(dealerCar));
        when(dealerCarMapper.toDto(dealerCar)).thenReturn(dealerCarResponseDto);

        DealerCarResponseDto result = dealerCarService.getDealerCarById(1);

        assertNotNull(result);
        assertEquals(dealerCarResponseDto, result);
    }

    @Test
    void testUpdateDealerCar() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.of(dealerCar));
        doNothing().when(dealerCarMapper).updateEntityFromDto(dealerCarRequestDto, dealerCar);
        when(dealerRepository.findById(dealerCarRequestDto.getDealerId())).thenReturn(Optional.of(dealer));
        when(carRepository.findById(dealerCarRequestDto.getCarId())).thenReturn(Optional.of(car));
        when(dealerCarRepository.save(dealerCar)).thenReturn(dealerCar);

        dealerCarService.updateDealerCar(1, dealerCarRequestDto);

        verify(dealerCarRepository).save(dealerCar);
        verify(dealerRepository).findById(dealerCarRequestDto.getDealerId());
        verify(carRepository).findById(dealerCarRequestDto.getCarId());
    }

    @Test
    void testDeleteDealerCarById() {
        when(dealerCarRepository.findById(1)).thenReturn(Optional.of(dealerCar));

        dealerCarService.deleteDealerCarById(1);

        verify(dealerCarRepository).deleteById(1);
    }

    @Test
    void testGetDealersCarByCarId() {
        List<DealerCar> dealerCars = Collections.singletonList(dealerCar);
        when(carRepository.findById(2)).thenReturn(Optional.of(car));
        when(dealerCarRepository.findByCar(car)).thenReturn(dealerCars);
        when(dealerCarMapper.toDto(dealerCar)).thenReturn(dealerCarResponseDto);

        List<DealerCarResponseDto> result = dealerCarService.getDealersCarByCarId(2);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dealerCarResponseDto, result.get(0));
    }

    @Test
    void testGetDealersCarsByDealerId() {
        List<DealerCar> dealerCars = Collections.singletonList(dealerCar);
        when(dealerRepository.findById(1)).thenReturn(Optional.of(dealer));
        when(dealerCarRepository.findByDealer(dealer)).thenReturn(dealerCars);
        when(dealerCarMapper.toDto(dealerCar)).thenReturn(dealerCarResponseDto);

        List<DealerCarResponseDto> result = dealerCarService.getDealersCarsByDealerId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dealerCarResponseDto, result.get(0));
    }
}
