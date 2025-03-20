package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.TestDriveRequestDto;
import org.example.dto.response.TestDriveResponseDto;
import org.example.service.TestDriveService;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/test-drives")
@RequiredArgsConstructor
public class TestDriveController {

    private final TestDriveService testDriveService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TestDriveResponseDto>> getAllTestDrives() {
        return ResponseEntity.ok(testDriveService.getAllTestDrives());
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TestDriveResponseDto>> getTestDrivesByClient(@PathVariable int clientId) {
        return ResponseEntity.ok(testDriveService.getTestDrivesByClientId(clientId));
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<TestDriveResponseDto>> getTestDrivesByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(testDriveService.getTestDrivesByDate(date));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TestDriveResponseDto> getTestDriveById(@PathVariable int id) {
        return ResponseEntity.ok(testDriveService.getTestDriveById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> createTestDrive(@Valid @RequestBody TestDriveRequestDto testDriveDto) {
        testDriveService.addTestDrive(testDriveDto);
        return ResponseEntity.ok("Test drive added successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> updateTestDrive(@PathVariable int id, @Valid @RequestBody TestDriveRequestDto testDriveDto) {
        testDriveService.updateTestDrive(id, testDriveDto);
        return ResponseEntity.ok("Test drive updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTestDrive(@PathVariable int id) {
        testDriveService.deleteTestDriveById(id);
        return ResponseEntity.ok("Test drive deleted successfully");
    }
}
