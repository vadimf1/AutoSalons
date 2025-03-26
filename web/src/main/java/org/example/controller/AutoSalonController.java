package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.AutoSalonFilterRequest;
import org.example.dto.request.AutoSalonRequestDto;
import org.example.dto.response.AutoSalonResponseDto;
import org.example.service.AutoSalonService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<AutoSalonResponseDto>> getAllAutoSalons() {
        return ResponseEntity.ok(autoSalonService.getAllAutoSalons());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<AutoSalonResponseDto> getAutoSalonById(@PathVariable int id) {
        return ResponseEntity.ok(autoSalonService.getAutoSalonById(id));
    }

    @PostMapping("/filter")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AutoSalonResponseDto>> getFilteredAutoSalons(@RequestBody AutoSalonFilterRequest autoSalonFilterRequest) {
        return ResponseEntity.ok(autoSalonService.getFilteredAutoSalons(autoSalonFilterRequest));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> addAutoSalon(@Valid @RequestBody AutoSalonRequestDto autoSalonDto) {
        autoSalonService.addAutoSalon(autoSalonDto);
        return ResponseEntity.ok("Auto salon added successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> updateAutoSalon(@PathVariable int id, @Valid @RequestBody AutoSalonRequestDto autoSalonDto) {
        autoSalonService.updateAutoSalon(id, autoSalonDto);
        return ResponseEntity.ok("Auto salon updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAutoSalon(@PathVariable("id") int id) {
        autoSalonService.deleteAutoSalonById(id);
        return ResponseEntity.ok("Auto salon deleted successfully");
    }
}
