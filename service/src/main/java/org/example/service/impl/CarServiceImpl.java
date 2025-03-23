package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.AddCarDto;
import org.example.dto.request.UpdateCarDto;
import org.example.dto.response.CarResponseDto;
import org.example.model.Dealer;
import org.example.model.DealerCar;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.DealerCarRepository;
import org.example.repository.DealerRepository;
import org.example.repository.specification.CarSpecification;
import org.example.dto.request.CarFilterRequest;
import org.example.exception.ServiceException;
import org.example.mapper.CarMapper;
import org.example.model.AutoSalon;
import org.example.model.Car;
import org.example.service.CarService;
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
    private final DealerCarRepository dealerCarRepository;
    private final DealerRepository dealerRepository;

    @Transactional
    public void addCar(AddCarDto addCarDto) {
        Car car = carMapper.toEntity(addCarDto);

        addAutoSalonsToCar(car, addCarDto.getAutoSalonIds());

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

    private void addAutoSalonsToCar(Car car, List<Integer> autoSalonIds) {
        for (Integer autoSalonId : autoSalonIds) {
            AutoSalon autoSalon = autoSalonRepository.findById(autoSalonId)
                    .orElseThrow(() -> new ServiceException(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + autoSalonId));

            car.addAutoSalon(autoSalon);
        }
    }

    @Transactional
    public List<CarResponseDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Transactional
    public CarResponseDto getCarById(int carId) {
        return carRepository.findById(carId)
                .map(carMapper::toDto)
                .orElseThrow(() -> new ServiceException(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + carId));
    }

    @Transactional
    public List<CarResponseDto> getFilteredCars(CarFilterRequest carFilterRequest) {
        return carRepository.findAll(CarSpecification.filterBy(
                        carFilterRequest.getVin(),
                        carFilterRequest.getMake(),
                        carFilterRequest.getModel(),
                        carFilterRequest.getYear()
                ))
                .stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Transactional
    public void updateCar(int carId, UpdateCarDto updateCarDto) {
        Car car = carRepository.findById(carId)
                        .orElseThrow(() -> new ServiceException(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + carId));

        carMapper.updateEntityFromDto(updateCarDto, car);

        car.setAutoSalons(new HashSet<>());
        addAutoSalonsToCar(car, updateCarDto.getAutoSalonIds());

        carRepository.save(car);
    }

    @Transactional
    public void deleteCarById(int carId) {
        carRepository.findById(carId)
                        .orElseThrow(() -> new ServiceException(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + carId));
        carRepository.deleteById(carId);
    }

}
