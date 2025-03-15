package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aop.Loggable;
import org.example.dto.request.DealerCarRequestDto;
import org.example.dto.response.DealerCarResponseDto;
import org.example.service.DealerCarService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dealer-cars")
@RequiredArgsConstructor
public class DealerCarController {

    private final DealerCarService dealerCarService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<DealerCarResponseDto>> getAllDealerCars() {
        return ResponseEntity.ok(dealerCarService.getAllDealerCars());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<DealerCarResponseDto> getDealerCarById(@PathVariable Integer id) {
        return ResponseEntity.ok(dealerCarService.getDealerCarById(id));
    }

    @Loggable
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> addDealerCar(@Valid @RequestBody DealerCarRequestDto dealerCarDto) {
        dealerCarService.addDealerCar(dealerCarDto);
        return ResponseEntity.ok("Dealer car added successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> updateDealerCar(@PathVariable int id, @Valid @RequestBody DealerCarRequestDto dealerCarDto) {
        dealerCarService.updateDealerCar(id, dealerCarDto);
        return ResponseEntity.ok("Dealer car updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDealerCar(@PathVariable("id") Integer id) {
        dealerCarService.deleteDealerCarById(id);
        return ResponseEntity.ok("Dealer car added successfully");
    }
}