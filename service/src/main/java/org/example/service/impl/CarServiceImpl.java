package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.AddCarDto;
import org.example.mapper.AddCarMapper;
import org.example.model.Dealer;
import org.example.model.DealerCar;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.DealerCarRepository;
import org.example.repository.DealerRepository;
import org.example.repository.specification.CarSpecification;
import org.example.dto.AutoSalonDto;
import org.example.dto.CarDto;
import org.example.dto.CarRequest;
import org.example.exception.ServiceException;
import org.example.mapper.CarMapper;
import org.example.model.AutoSalon;
import org.example.model.Car;
import org.example.service.CarService;
import org.example.service.DealerCarService;
import org.example.service.DealerService;
import org.example.util.error.AutoSalonExceptionCode;
import org.example.util.error.CarExceptionCode;
import org.example.util.error.DealerExceptionCode;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final AutoSalonRepository autoSalonRepository;
    private final CarMapper carMapper;
    private final AddCarMapper addCarMapper;
    private final DealerCarRepository dealerCarRepository;
    private final DealerRepository dealerRepository;

    @Transactional
    public void addCar(AddCarDto addCarDto) {
        if (addCarDto.getId() != null) {
            throw new ServiceException(CarExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }

        Car car = addCarMapper.toEntity(addCarDto);
        car.setAutoSalons(new HashSet<>());

        for (AutoSalonDto autoSalonDto : addCarDto.getAutoSalons()) {
            if (autoSalonDto.getId() == null) {
                throw new ServiceException(CarExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
            }

            AutoSalon autoSalon = autoSalonRepository.findById(autoSalonDto.getId())
                    .orElseThrow(() -> new ServiceException(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + autoSalonDto.getId()));

            car.addAutoSalon(autoSalon);
        }
        carRepository.save(car);

        Dealer dealer = dealerRepository.findById(addCarDto.getDealerId())
                .orElseThrow(() -> new ServiceException(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + addCarDto.getDealerId()));

        DealerCar dealerCar = DealerCar.builder()
                .dealer(dealer)
                .car(car)
                .price(addCarDto.getPrice())
                .build();

        dealerCarRepository.save(dealerCar);
    }

    @Transactional
    public List<CarDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Transactional
    public CarDto getCarById(int carId) {
        return carRepository.findById(carId)
                .map(carMapper::toDto)
                .orElseThrow(() -> new ServiceException(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + carId));
    }

    @Transactional
    public List<CarDto> searchCars(CarRequest carRequest) {
        return carRepository.findAll(CarSpecification.filterBy(
                        carRequest.getVin(),
                        carRequest.getMake(),
                        carRequest.getModel(),
                        carRequest.getYear()
                ))
                .stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Transactional
    public void updateCar(CarDto updatedCarDto) {
        if (updatedCarDto.getId() == null) {
            throw new ServiceException(CarExceptionCode.ID_FIELD_NOT_NULL.getMessage());
        }
        carRepository.save(carMapper.toEntity(updatedCarDto));
    }

    @Transactional
    public void deleteCarById(int carId) {
        carRepository.deleteById(carId);
    }
}
