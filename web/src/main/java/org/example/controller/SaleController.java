package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aop.Loggable;
import org.example.dto.request.SaleRequestDto;
import org.example.dto.response.SaleResponseDto;
import org.example.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<SaleResponseDto>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<SaleResponseDto> getSaleById(@PathVariable int id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SaleResponseDto>> getSaleByClientId(@PathVariable int clientId) {
        return ResponseEntity.ok(saleService.getSaleByClientId(clientId));
    }

    @Loggable
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> addSale(@Valid @RequestBody SaleRequestDto saleDto) {
        saleService.addSale(saleDto);
        return ResponseEntity.ok("Sale added successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> updateSale(@PathVariable int id, @Valid @RequestBody SaleRequestDto saleDto) {
        saleService.updateSale(id, saleDto);
        return ResponseEntity.ok("Sale updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSale(@PathVariable("id") int id) {
        saleService.deleteSaleById(id);
        return ResponseEntity.ok("Sale deleted successfully");
    }
}