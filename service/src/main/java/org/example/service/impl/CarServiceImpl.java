package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.specification.CarSpecification;
import org.example.dto.AutoSalonDto;
import org.example.dto.CarDto;
import org.example.dto.CarRequest;
import org.example.exception.ServiceException;
import org.example.mapper.CarMapper;
import org.example.model.AutoSalon;
import org.example.model.Car;
import org.example.service.CarService;
import org.example.util.error.CarExceptionCode;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final AutoSalonRepository autoSalonRepository;
    private final CarMapper carMapper;

    @Transactional
    public void addCar(CarDto carDto) {
        if (carDto.getId() != null) {
            throw new ServiceException(CarExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }

        Car car = carMapper.toEntity(carDto);
        car.setAutoSalons(new HashSet<>());

        for (AutoSalonDto autoSalonDto : carDto.getAutoSalons()) {
            if (autoSalonDto.getId() == null) {
                throw new ServiceException(CarExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
            }

            AutoSalon autoSalon = autoSalonRepository.findById(autoSalonDto.getId())
                    .orElseThrow(() -> new ServiceException(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + autoSalonDto.getId()));

            car.addAutoSalon(autoSalon);
        }

        carRepository.save(car);
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
            throw new ServiceException(CarExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }
        carRepository.save(carMapper.toEntity(updatedCarDto));
    }

    @Transactional
    public void deleteCarById(int carId) {
        carRepository.deleteById(carId);
    }
}
