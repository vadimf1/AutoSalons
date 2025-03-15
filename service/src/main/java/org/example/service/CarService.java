package org.example.service;

import org.example.dto.request.AddCarDto;
import org.example.dto.request.UpdateCarDto;
import org.example.dto.response.CarResponseDto;
import org.example.dto.request.CarRequest;

import java.util.List;

public interface CarService {
    void addCar(AddCarDto addCarDto);

    List<CarResponseDto> getAllCars();

    CarResponseDto getCarById(int carId);

    List<CarResponseDto> searchCars(CarRequest carRequest);

    void updateCar(int carId, UpdateCarDto updateCarDto);

    void deleteCarById(int carId);
}