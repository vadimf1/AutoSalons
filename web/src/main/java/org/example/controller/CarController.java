package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aop.Loggable;
import org.example.dto.request.UpdateCarDto;
import org.example.dto.response.CarResponseDto;
import org.example.dto.request.AddCarDto;
import org.example.dto.request.CarRequest;
import org.example.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @Loggable
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<CarResponseDto> getCarById(@PathVariable int id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PostMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CarResponseDto>> searchCar(@RequestBody CarRequest carRequest) {
        return ResponseEntity.ok(carService.searchCars(carRequest));
    }

    @Loggable
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> addCar(@Valid @RequestBody AddCarDto addCarDto) {
        carService.addCar(addCarDto);
        return ResponseEntity.ok("Car added successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> updateCar(@PathVariable int id, @Valid @RequestBody UpdateCarDto updateCarDto) {
        carService.updateCar(id, updateCarDto);
        return ResponseEntity.ok("Car updated successfully");
    }

    @Loggable
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCar(@PathVariable("id") int id) {
        carService.deleteCarById(id);
        return ResponseEntity.ok("Car deleted successfully");
    }
}
