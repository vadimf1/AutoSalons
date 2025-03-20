package org.example.service;

import org.example.dto.request.AddCarDto;
import org.example.dto.request.UpdateCarDto;
import org.example.dto.response.CarResponseDto;
import org.example.model.AutoSalon;
import org.example.model.Car;
import org.example.model.Dealer;
import org.example.model.DealerCar;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.DealerCarRepository;
import org.example.repository.DealerRepository;
import org.example.mapper.CarMapper;
import org.example.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private AutoSalonRepository autoSalonRepository;

    @Mock
    private CarMapper carMapper;

    @Mock
    private DealerCarRepository dealerCarRepository;

    @Mock
    private DealerRepository dealerRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private AddCarDto addCarDto;
    private UpdateCarDto updateCarDto;
    private Car car;
    private Dealer dealer;
    private AutoSalon autoSalon;
    private DealerCar dealerCar;
    private CarResponseDto carResponseDto;

    @BeforeEach
    void setUp() {
        addCarDto = AddCarDto.builder()
                .make("Toyota")
                .model("Corolla")
                .vin("1HGBH41JXMN109186")
                .year(2020)
                .color("Red")
                .bodyType("Sedan")
                .engineType("Petrol")
                .fuelType("Gasoline")
                .transmission("Automatic")
                .status("New")
                .autoSalonIds(Arrays.asList(1, 2))
                .dealerId(1)
                .price(BigDecimal.valueOf(15000))
                .build();

        updateCarDto = UpdateCarDto.builder()
                .make("Toyota")
                .model("Camry")
                .vin("1HGBH41JXMN109187")
                .year(2021)
                .color("Blue")
                .bodyType("Sedan")
                .engineType("Hybrid")
                .fuelType("Hybrid")
                .transmission("Automatic")
                .status("Used")
                .autoSalonIds(Arrays.asList(1, 3))
                .dealerId(2)
                .price(BigDecimal.valueOf(18000))
                .build();

        car = new Car();
        dealer = new Dealer();
        autoSalon = new AutoSalon();
        dealerCar = new DealerCar();

        carResponseDto = CarResponseDto.builder()
                .id(1)
                .make("Toyota")
                .model("Corolla")
                .vin("1HGBH41JXMN109186")
                .year(2020)
                .color("Red")
                .bodyType("Sedan")
                .engineType("Petrol")
                .fuelType("Gasoline")
                .transmission("Automatic")
                .status("New")
                .build();
    }

    @Test
    void testAddCar() {
        when(carMapper.toEntity(addCarDto)).thenReturn(car);
        when(autoSalonRepository.findById(1)).thenReturn(Optional.of(autoSalon));
        when(autoSalonRepository.findById(2)).thenReturn(Optional.of(autoSalon));
        when(dealerRepository.findById(addCarDto.getDealerId())).thenReturn(Optional.of(dealer));
        when(carRepository.save(car)).thenReturn(car);
        when(dealerCarRepository.save(any(DealerCar.class))).thenReturn(dealerCar);

        carService.addCar(addCarDto);

        verify(carMapper).toEntity(addCarDto);
        verify(autoSalonRepository, times(2)).findById(anyInt());
        verify(dealerRepository).findById(addCarDto.getDealerId());
        verify(carRepository).save(car);
        verify(dealerCarRepository).save(any(DealerCar.class));
    }

    @Test
    void testGetAllCars() {
        List<Car> cars = Collections.singletonList(car);
        when(carRepository.findAll()).thenReturn(cars);
        when(carMapper.toDto(car)).thenReturn(carResponseDto);

        List<CarResponseDto> result = carService.getAllCars();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(carResponseDto, result.get(0));
    }

    @Test
    void testGetCarById() {
        when(carRepository.findById(1)).thenReturn(Optional.of(car));
        when(carMapper.toDto(car)).thenReturn(carResponseDto);

        CarResponseDto result = carService.getCarById(1);

        assertNotNull(result);
        assertEquals(carResponseDto, result);
    }

    @Test
    void testUpdateCar() {
        when(carRepository.findById(1)).thenReturn(Optional.of(car));
        doNothing().when(carMapper).updateEntityFromDto(updateCarDto, car);
        when(autoSalonRepository.findById(1)).thenReturn(Optional.of(autoSalon));
        when(autoSalonRepository.findById(3)).thenReturn(Optional.of(autoSalon));
        when(carRepository.save(car)).thenReturn(car);

        carService.updateCar(1, updateCarDto);

        verify(carRepository).save(car);
        verify(autoSalonRepository, times(2)).findById(anyInt());
    }

    @Test
    void testDeleteCarById() {
        when(carRepository.findById(1)).thenReturn(Optional.of(car));

        carService.deleteCarById(1);

        verify(carRepository).deleteById(1);
    }
}
