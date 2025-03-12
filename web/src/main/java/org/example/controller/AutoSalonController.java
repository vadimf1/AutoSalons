package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aop.Loggable;
import org.example.dto.AutoSalonDto;
import org.example.service.AutoSalonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auto-salons")
@RequiredArgsConstructor
public class AutoSalonController {

    private final AutoSalonService autoSalonService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public List<AutoSalonDto> getAllAutoSalons() {
        return autoSalonService.getAllAutoSalons();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public AutoSalonDto getAutoSalonById(@PathVariable int id) {
        return autoSalonService.getAutoSalonById(id);
    }

    @Loggable
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void addAutoSalon(@Valid @RequestBody AutoSalonDto autoSalonDto) {
        autoSalonService.addAutoSalon(autoSalonDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void updateAutoSalon(@Valid @RequestBody AutoSalonDto autoSalonDto) {
        autoSalonService.updateAutoSalon(autoSalonDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAutoSalon(@PathVariable("id") int id) {
        autoSalonService.deleteAutoSalonById(id);
    }
}
