package org.example.service;

import org.example.dto.AddCarDto;
import org.example.dto.CarDto;
import org.example.dto.CarRequest;
import org.example.model.Car;

import java.util.List;

public interface CarService {
    void addCar(AddCarDto addCarDto);

    List<CarDto> getAllCars();

    CarDto getCarById(int carId);

    List<CarDto> searchCars(CarRequest carRequest);

    void updateCar(CarDto updatedCarDto);

    void deleteCarById(int carId);
}