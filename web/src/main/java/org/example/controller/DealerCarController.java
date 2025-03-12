package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aop.Loggable;
import org.example.dto.DealerCarDto;
import org.example.service.DealerCarService;
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
    public List<DealerCarDto> getAllDealerCars() {
        return dealerCarService.getAllDealerCars();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public DealerCarDto getDealerCarById(@PathVariable Integer id) {
        return dealerCarService.getDealerCarById(id);
    }

    @Loggable
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void addDealerCar(@Valid @RequestBody DealerCarDto dealerCarDto) {
        dealerCarService.addDealerCar(dealerCarDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void updateDealerCar(@Valid @RequestBody DealerCarDto dealerCarDto) {
        dealerCarService.updateDealerCar(dealerCarDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDealerCar(@PathVariable("id") Integer id) {
        dealerCarService.deleteDealerCarById(id);
    }
}