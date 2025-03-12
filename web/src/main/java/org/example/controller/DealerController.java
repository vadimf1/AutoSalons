package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aop.Loggable;
import org.example.dto.DealerDto;
import org.example.service.DealerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dealers")
@RequiredArgsConstructor
public class DealerController {

    private final DealerService dealerService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public List<DealerDto> getAllDealers() {
        return dealerService.getAllDealers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public DealerDto getDealerById(@PathVariable int id) {
        return dealerService.getDealerById(id);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public DealerDto getDealerByName(@PathVariable String name) {
        return dealerService.getDealerByName(name);
    }

    @Loggable
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void addDealer(@Valid @RequestBody DealerDto dealerDto) {
        dealerService.addDealer(dealerDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void updateDealer(@Valid @RequestBody DealerDto dealerDto) {
        dealerService.updateDealer(dealerDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDealer(@PathVariable("id") int id) {
        dealerService.deleteDealerById(id);
    }
}
