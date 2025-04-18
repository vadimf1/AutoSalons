package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.DealerFilterRequest;
import org.example.dto.request.DealerRequestDto;
import org.example.dto.response.DealerResponseDto;
import org.example.service.DealerService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<DealerResponseDto>> getAllDealers() {
        return ResponseEntity.ok(dealerService.getAllDealers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<DealerResponseDto> getDealerById(@PathVariable int id) {
        return ResponseEntity.ok(dealerService.getDealerById(id));
    }

    @PostMapping("/filter")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DealerResponseDto>> getFilteredDealers(@RequestBody DealerFilterRequest dealerFilterRequest) {
        return ResponseEntity.ok(dealerService.getFilteredDealers(dealerFilterRequest));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> addDealer(@Valid @RequestBody DealerRequestDto dealerDto) {
        dealerService.addDealer(dealerDto);
        return ResponseEntity.ok("Dealer added successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> updateDealer(@PathVariable int id, @Valid @RequestBody DealerRequestDto dealerDto) {
        dealerService.updateDealer(id, dealerDto);
        return ResponseEntity.ok("Dealer updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDealer(@PathVariable("id") int id) {
        dealerService.deleteDealerById(id);
        return ResponseEntity.ok("Dealer deleted successfully");
    }
}
